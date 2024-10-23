package fr.atticap.bookworm.ui.features.tag

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.persistence.repository.TagRepository
import fr.atticap.bookworm.service.TagService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
open class TagRoute

@Serializable
data class EditTagRoute(val id: String) : TagRoute()

@Serializable
data object CreateTagRoute : TagRoute()

sealed interface DialogResult {
    data object None : DialogResult
    data object Pending : DialogResult
    data class Saved(val tag: Tag) : DialogResult
}

class TagViewModel(
    savedStateHandle: SavedStateHandle,
    tagRepository: TagRepository,
    private val tagService: TagService
) : ViewModel() {
    private val tagRoute = savedStateHandle.toRoute<TagRoute>()

    val tag: Flow<Option<Tag>> = when (tagRoute) {
        is CreateTagRoute -> flowOf(None)
        is EditTagRoute -> tagRepository.getTagById(Uuid.parse(tagRoute.id)).map(::Some)
        else -> flowOf(None)
    }

    var name by mutableStateOf("")
    var color by mutableStateOf(Color.Unspecified)

    val tagFormState: StateFlow<Either<List<TagFormValidation>, TagForm>> =
        snapshotFlow { TagForm(name, color) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, TagForm.Empty)

    private val _dialogResult: MutableStateFlow<DialogResult> = MutableStateFlow(DialogResult.None)
    val dialogResult: StateFlow<DialogResult> = _dialogResult.asStateFlow()

    fun createTag() {
        viewModelScope.launch(Dispatchers.IO) {
            val tagForm = tagFormState.first()
            val editTag: Option<Tag> = tag.first()

            if (tagForm is Either.Right) {
                _dialogResult.update { DialogResult.Pending }
                val newTag = editTag.fold(
                    ifEmpty = { tagForm.value.toTag() },
                    ifSome = { tagForm.value.toTag().copy(id = it.id) }
                )

                tagService.update(newTag)
                _dialogResult.update { DialogResult.Saved(newTag) }
            }
        }
    }

    private fun TagForm.toTag(): Tag = Tag(
        name = name,
        color = color,
    )
}
