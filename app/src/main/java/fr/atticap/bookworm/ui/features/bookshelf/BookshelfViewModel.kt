package fr.atticap.bookworm.ui.features.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BookshelfViewModel(
    bookRepository: BookRepository,
    private val bookService: BookService
) : ViewModel(
) {
    val bookshelves: Flow<Map<Bookshelf, List<Book>>> = bookRepository.bookshelves

    fun moveToBookshelf(id: Uuid, source: Uuid, destination: Bookshelf) {
        viewModelScope.launch(Dispatchers.IO) {
            bookService.moveToBookshelf(id, source, destination)
        }
    }

    fun addBookToBookshelf(book: Book, bookshelf: Bookshelf) {
        viewModelScope.launch(Dispatchers.IO) {
            bookService.updateBook(book)
            bookService.addToBookshelf(book, bookshelf)
        }
    }

    fun addBookshelf(bookshelf: Bookshelf) {
        viewModelScope.launch(Dispatchers.IO) {
            bookService.updateBookshelf(bookshelf)
        }
    }
}