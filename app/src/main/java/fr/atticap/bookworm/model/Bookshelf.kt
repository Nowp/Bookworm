package fr.atticap.bookworm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "bookshelf")
data class Bookshelf(
    @PrimaryKey
    val id: Uuid = Uuid.random(),
    val name: String,
)
