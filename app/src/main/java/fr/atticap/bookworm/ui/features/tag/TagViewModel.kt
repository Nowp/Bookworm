package fr.atticap.bookworm.ui.features.tag

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.Ior
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.persistence.repository.TagRepository
import fr.atticap.bookworm.service.TagService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
open class TagRoute

@Serializable data class EditTagRoute(val id: String): TagRoute()
@Serializable data object CreateTagRoute: TagRoute()

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

    private val _tagFormState: MutableStateFlow<Ior<List<TagFormValidation>, TagForm>> =
        MutableStateFlow(TagForm(name = "", color = Color.Unspecified))

    val tagFormState: StateFlow<Ior<List<TagFormValidation>, TagForm>> =
        _tagFormState.asStateFlow()

    fun addTag() {
        tagFormState.value.fold(
            {},
            {
                viewModelScope.launch(Dispatchers.IO) {
                    tagService.update(Tag(name = it.name, color = it.color))
                }
            },
            { _, _ -> }
        )
    }

    fun setName(name: String) {
        _tagFormState.update {
            it.fold(
                { TagForm(name = "", color = Color.Unspecified) },
                { form -> TagForm(name = name, color = form.color) },
                { _, form -> TagForm(name = name, color = form.color) }
            )
        }
    }

    fun setColor(color: Color) {
        _tagFormState.update {
            it.fold(
                { TagForm(name = "", color = Color.Unspecified) },
                { form -> TagForm(name = form.name, color = color) },
                { _, form -> TagForm(name = form.name, color = color) }
            )
        }
    }
}