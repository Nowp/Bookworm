package fr.atticap.bookworm.ui.features.volume

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.persistence.repository.TagRepository
import fr.atticap.bookworm.service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

class VolumeViewModel(
    private val savedStateHandle: SavedStateHandle,
    bookRepository: BookRepository,
    tagRepository: TagRepository,
    private val bookService: BookService,
) : ViewModel() {
    private val volumeDetails = savedStateHandle.toRoute<VolumeRoute>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val createTag: Flow<Option<Tag>> =
        savedStateHandle.getStateFlow<String?>("createdTagId", null)
            .map { it.toOption() }
            .flatMapLatest { idOption ->
                idOption.fold(
                    { flowOf(None) },
                    { id -> tagRepository.getTagById(Uuid.parse(id)).map { Some(it) }}
                )
            }

    val volume: Flow<Pair<Volume, List<PositionedTag>>> =
        bookRepository.getBookWithTags(Uuid.parse(volumeDetails.id))

    init {
        handleAddCreatedTagsToVolume()
    }

    private fun handleAddCreatedTagsToVolume() {
        viewModelScope.launch(Dispatchers.IO) {
            createTag.combine(volume) { tag, volume -> tag to volume }
                .collectLatest { (tag, volumeWithTags) ->
                    val (volume, tags) = volumeWithTags
                    tag.fold(
                        ifEmpty = { return@collectLatest },
                        ifSome = {
                            bookService.addTagToVolume(
                                volume,
                                it,
                                tags.maxBy(PositionedTag::pos).pos + 1
                            )
                        }
                    )
                }
        }
    }
}