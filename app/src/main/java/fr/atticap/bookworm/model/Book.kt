package fr.atticap.bookworm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "book")
data class Book @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Uuid,
    val title: String,
    val author: String,
    val year: Int
)
