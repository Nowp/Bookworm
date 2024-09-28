package fr.atticap.bookworm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "bookshelf")
data class Bookshelf @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Uuid,
    val name: String,
)
