package fr.atticap.bookworm.ui.features.tag

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import arrow.optics.optics


sealed interface TagFormValidation {
    data object EmptyName : TagFormValidation
    data object ColorUnspecified : TagFormValidation
}

@optics
data class TagForm private constructor(val name: String, val color: Color) {
    companion object {
        val Empty: Either.Left<List<TagFormValidation>> =
            Companion("", Color.Unspecified) as Either.Left<List<TagFormValidation>>

        operator fun invoke(
            name: String, color: Color
        ): Either<List<TagFormValidation>, TagForm> = either {
            zipOrAccumulate(
                { ensure(name.isNotBlank()) { TagFormValidation.EmptyName } },
                { ensure(color.isSpecified) { TagFormValidation.ColorUnspecified } },
            ) { _, _ -> TagForm(name, color) }
        }
    }
}