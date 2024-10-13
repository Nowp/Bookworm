package fr.atticap.bookworm.model

import androidx.room.Embedded

data class PositionedTag(
    @Embedded
    var tag: Tag,
    var pos: Int
)
