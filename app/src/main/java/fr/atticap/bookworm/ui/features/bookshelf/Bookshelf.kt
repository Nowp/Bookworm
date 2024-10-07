package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.atticap.bookworm.model.Volume
import kotlin.uuid.ExperimentalUuidApi


@Composable
fun Bookshelf(
    modifier: Modifier = Modifier,
    volumes: List<Volume>,
    onAddBook: () -> Unit = {},
    onVolumeClick: (Volume) -> Unit = {}
) {
    LazyRow(
        modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(volumes) { book ->
            Volume(
                Modifier
                    .requiredHeight(400.dp)
                    .requiredWidth(100.dp)
                    .clickable(onClick = { onVolumeClick(book) }),
                title = book.title
            )
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .requiredWidth(100.dp),
                onClick = onAddBook
            ) {
                Text(modifier = Modifier.fillMaxSize(), text = "Add book")
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun PreviewBookshelf() {
    Bookshelf(
        volumes = listOf(
            Volume(title = "Book 1", author = "", year = 2000),
            Volume(title = "Book 2", author = "", year = 2000),
            Volume(title = "Book 3", author = "", year = 2000)
        )
    )
}