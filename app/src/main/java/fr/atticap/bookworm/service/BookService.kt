package fr.atticap.bookworm.service

import android.util.Log
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.persistence.dao.BookDao
import kotlinx.coroutines.flow.first
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BookService(private val bookDao: BookDao) {

    suspend fun moveToBookshelf(bookId: Uuid, sourceBookshelf: Uuid, destinationBookshelf: Bookshelf) {
        Log.d("BookService", "Moving book $bookId from $sourceBookshelf to $destinationBookshelf")

        val book = bookDao.getBookById(bookId).first()
        bookDao.deleteBookshelfContent(BookshelfContent(sourceBookshelf, book.id))
        bookDao.insertBookshelfContent(BookshelfContent(destinationBookshelf.id, book.id))
    }

    fun addToBookshelf(book: Book, bookshelf: Bookshelf) {
        Log.d("BookService", "Adding book $book to bookshelf $bookshelf")
        bookDao.insertBookshelfContent(BookshelfContent(bookshelf.id, book.id))
    }

    fun updateBookshelf(bookshelf: Bookshelf) {
        Log.d("BookService", "Updating bookshelf $bookshelf")
        bookDao.updateBookshelf(bookshelf)
    }

    fun updateBook(book: Book) {
        Log.d("BookService", "Updating book $book")
        bookDao.updateBook(book)
    }
}