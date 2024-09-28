package fr.atticap.bookworm.persistence.repository

import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.persistence.dao.BookshelfDao
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookshelfDao: BookshelfDao) {
    val books: Flow<List<Book>> = bookshelfDao.getAllBooks()
}