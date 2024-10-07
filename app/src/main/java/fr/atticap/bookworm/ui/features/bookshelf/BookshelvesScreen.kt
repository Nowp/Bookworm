package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.ui.gestures.detectPinch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookshelvesScreen(onNavigateVolume: (Volume) -> Unit, onZoomOut: () -> Unit) {
    val bookshelvesViewModel = koinViewModel<BookshelvesViewModel>()

    val bookshelves: Map<Bookshelf, List<Volume>>
            by bookshelvesViewModel.bookshelves.collectAsState(mapOf())

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectPinch(onPinchOut = onZoomOut)
        }
    ) {
        items(items = bookshelves.entries.toList()) { (bookshelf, volumes) ->
            Text(text = bookshelf.name)
            Bookshelf(
                volumes = volumes,
                onVolumeClick = onNavigateVolume,
                onAddBook = {
                    bookshelvesViewModel.addBookToBookshelf(bookshelf)
                }
            )
        }
        item {
            Button(onClick = { bookshelvesViewModel.addBookshelf() }) {
                Text(text = "Add bookshelf")
            }
        }
    }
}