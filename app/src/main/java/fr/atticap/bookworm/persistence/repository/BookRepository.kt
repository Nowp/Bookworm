package fr.atticap.bookworm.persistence.repository

import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.persistence.dao.BookDao
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BookRepository(private val bookDao: BookDao) {
    val books: Flow<List<Book>> = bookDao.getAllBooks()
    val bookshelves: Flow<Map<Bookshelf, List<Book>>> = bookDao.getBookshelvesWithBooks()

    fun getBookById(id: Uuid): Flow<Book> {
        return bookDao.getBookById(id)
    }

}