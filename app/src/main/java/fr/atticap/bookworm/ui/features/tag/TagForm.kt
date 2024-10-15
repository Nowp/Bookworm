package fr.atticap.bookworm.ui.features.tag

import androidx.compose.ui.graphics.Color
import arrow.core.Ior
import arrow.core.raise.ior


sealed interface TagFormValidation {
    data object EmptyName : TagFormValidation
    data object ColorUnspecified : TagFormValidation
}

data class TagForm private constructor(val name: String, val color: Color) {
    companion object {
        operator fun invoke(
            name: String, color: Color
        ): Ior<List<TagFormValidation>, TagForm> = ior(List<TagFormValidation>::plus) {
            TagForm(name, color)
        }
    }
}