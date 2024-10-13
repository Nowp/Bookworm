package fr.atticap.bookworm.ui.features.volume

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.getOrElse
import arrow.core.toOption
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.uuid.Uuid

class VolumeViewModel(
    savedStateHandle: SavedStateHandle,
    bookRepository: BookRepository,
    private val bookService: BookService
) : ViewModel() {
    private val volumeDetails = savedStateHandle.toRoute<VolumeRoute>()
    val volume: Flow<Pair<Volume, List<PositionedTag>>> =
        bookRepository.getBookWithTags(Uuid.parse(volumeDetails.id))

    fun addTagToVolume() {
        viewModelScope.launch(Dispatchers.IO) {
            val (volume, tags) = volume.first()

            bookService.addTagToVolume(
                volume, Tag(
                    name = "test",
                    color = Color(
                        Random.nextInt(until = 255),
                        Random.nextInt(until = 255),
                        Random.nextInt(until = 255)
                    )
                ),
                pos = tags
                    .maxByOrNull(PositionedTag::pos)
                    .toOption().map(PositionedTag::pos)
                    .getOrElse { 0 } + 1
            )
        }
    }
}