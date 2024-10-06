package fr.atticap.bookworm.service

import android.util.Log
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.dao.BookDao
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class BookService(private val bookDao: BookDao) {

    fun addToBookshelf(volume: Volume, bookshelf: Bookshelf) {
        Log.d("BookService", "Adding book $volume to bookshelf $bookshelf")
        bookDao.insertBookshelfContent(BookshelfContent(bookshelf.id, volume.id))
    }

    fun updateBookshelf(bookshelf: Bookshelf) {
        Log.d("BookService", "Updating bookshelf $bookshelf")
        bookDao.updateBookshelf(bookshelf)
    }

    fun updateBook(volume: Volume) {
        Log.d("BookService", "Updating book $volume")
        bookDao.updateBook(volume)
    }
}