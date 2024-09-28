package fr.atticap.bookworm.model

import androidx.room.Entity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "bookshelf_content", primaryKeys = ["bookshelfId", "bookId"])
data class BookshelfContent @OptIn(ExperimentalUuidApi::class) constructor(
    val bookshelfId: Uuid,
    val bookId: Uuid
)
