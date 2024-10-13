package fr.atticap.bookworm.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.Uuid

@Entity(
    tableName = "bookshelf_content",
    primaryKeys = ["bookshelfId", "bookId"],
    foreignKeys = [
        ForeignKey(
        entity = Bookshelf::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bookshelfId")
    ), ForeignKey(
        entity = Volume::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bookId")
    )],
    indices = [Index("bookshelfId"), Index("bookId")]
)
data class BookshelfContent(
    val bookshelfId: Uuid,
    val bookId: Uuid
)
