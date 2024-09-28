package fr.atticap.bookworm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.ui.features.bookshelf.Bookshelf
import fr.atticap.bookworm.ui.theme.BookwormTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val bookRepository by inject<BookRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            BookwormTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val books: List<Book> by bookRepository.books.collectAsState(listOf())

                        Bookshelf(
                            modifier = Modifier.padding(innerPadding),
                            books = books.map { it.title }
                        )
                    }

                }
            }
        }
    }
}
