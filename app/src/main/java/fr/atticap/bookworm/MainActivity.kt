package fr.atticap.bookworm

import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.ui.features.bookshelf.Bookshelf
import fr.atticap.bookworm.ui.features.bookshelf.BookshelfViewModel
import fr.atticap.bookworm.ui.theme.BookwormTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MainActivity : ComponentActivity() {
    private val bookshelfViewModel by viewModel<BookshelfViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalFoundationApi::class)
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
                        val bookshelves: Map<Bookshelf, List<Book>>
                                by bookshelfViewModel.bookshelves.collectAsState(mapOf())

                        val bookshelvesList: List<Map.Entry<Bookshelf, List<Book>>> =
                            bookshelves.entries.toList()

                        LazyColumn {
                            items(bookshelvesList) { (bookshelf, books) ->
                                Bookshelf(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .dragAndDropTarget(
                                            shouldStartDragAndDrop = accept@{ startEvent ->
                                                val hasValidMimeType =
                                                    startEvent
                                                        .mimeTypes()
                                                        .any { eventMimeType ->
                                                            eventMimeType.startsWith("text/")
                                                        }
                                                hasValidMimeType
                                            },
                                            target =
                                            object : DragAndDropTarget {
                                                override fun onDrop(
                                                    event: DragAndDropEvent
                                                ): Boolean {
                                                    val data = event.toAndroidDragEvent().clipData
                                                    bookshelfViewModel.moveToBookshelf(
                                                        Uuid.parse(
                                                            data.getItemAt(0).text.toString()
                                                        ),
                                                        Uuid.parse(
                                                            data.getItemAt(1).text.toString()
                                                        ),
                                                        bookshelf
                                                    )
                                                    return true
                                                }
                                            },
                                        ),
                                    books = books,
                                    addBook = {
                                        bookshelfViewModel.addBookToBookshelf(
                                            Book(
                                                title = "Book",
                                                author = "Author",
                                                year = 2000
                                            ),
                                            bookshelf
                                        )
                                    },
                                    onDrag = {
                                        startTransfer(
                                            DragAndDropTransferData(
                                                clipData = ClipData.newPlainText(
                                                    "book", it.id.toString()
                                                ).apply {
                                                    addItem(ClipData.Item(bookshelf.id.toString()))
                                                },
                                                flags = View.DRAG_FLAG_GLOBAL,
                                            )
                                        )
                                    }
                                )
                            }

                            item {
                                Button(
                                    onClick = {
                                        bookshelfViewModel.addBookshelf(
                                            Bookshelf(name = "New Bookshelf")
                                        )
                                    }) {
                                    Text("Add Bookshelf")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
