package fr.atticap.bookworm.model

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlin.uuid.Uuid


@Entity(
    tableName = "volume_tag", primaryKeys = ["volumeId", "tagId"], foreignKeys = [
        ForeignKey(
            entity = Volume::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("volumeId")
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        )
    ]
)
data class VolumeTag(
    val volumeId: Uuid,
    val tagId: Uuid,
    val pos: Int
)