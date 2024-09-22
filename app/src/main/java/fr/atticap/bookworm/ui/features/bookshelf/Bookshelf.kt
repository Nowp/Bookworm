package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun Bookshelf(modifier: Modifier = Modifier, books: List<String>) {
    LazyRow(
        modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(books) { book ->
            Book(
                Modifier
                    .requiredHeight((Random.nextInt(350, 400)).dp)
                    .requiredWidth(100.dp),
                title = book
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBookshelf() {
    Bookshelf(
        books = listOf(
            "Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6", "Book 7", "Book 8"
        )
    )
}