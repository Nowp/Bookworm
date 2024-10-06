package fr.atticap.bookworm.ui.features.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.service.BookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookshelvesViewModel(
    bookRepository: BookRepository,
    private val bookService: BookService
) : ViewModel() {
    val bookshelves: Flow<Map<Bookshelf, List<Volume>>> = bookRepository.bookshelves

    fun addBookToBookshelf(bookshelf: Bookshelf) {
        val volume = Volume(title = "New book", author = "", year = 2000)

        viewModelScope.launch(Dispatchers.IO) {
            bookService.updateBook(volume)
            bookService.addToBookshelf(volume, bookshelf)
        }
    }

    fun addBookshelf() {
        val bookshelf = Bookshelf(name = "New bookshelf")
        viewModelScope.launch(Dispatchers.IO) {
            bookService.updateBookshelf(bookshelf)
        }
    }
}