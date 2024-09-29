package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.DragAndDropSourceScope
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.atticap.bookworm.model.Book
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalFoundationApi::class, ExperimentalUuidApi::class)
@Composable
fun Bookshelf(modifier: Modifier = Modifier, books: List<Book>, addBook: () -> Unit = {}, onDrag: DragAndDropSourceScope.(Book) -> Unit = {}) {
    LazyRow(
        modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(books) { book ->
            val height = remember(book.id) { Random.nextInt(350, 400) }
            Book(
                Modifier
                    .requiredHeight(height.dp)
                    .requiredWidth(100.dp)
                    .dragAndDropSource {
                        detectTapGestures(onLongPress = { onDrag(book) })
                    },
                title = book.title
            )
        }

        item {
            Button(modifier = Modifier.requiredSize(100.dp, 100.dp), onClick = addBook) {
                Text(modifier = Modifier.fillMaxSize(), text = "Add book")
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewBookshelf() {
    Bookshelf(
        books = listOf(
            Book(title = "Book 1", author = "", year = 2000),
            Book(title = "Book 2", author = "", year = 2000),
            Book(title = "Book 3", author = "", year = 2000)
        )
    )
}