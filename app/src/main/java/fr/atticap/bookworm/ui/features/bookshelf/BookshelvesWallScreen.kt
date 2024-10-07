package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.ui.gestures.detectPinch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookshelvesWallScreen(onZoomIn: () -> Unit) {
    val bookshelvesViewModel = koinViewModel<BookshelvesViewModel>()

    val bookshelves: Map<Bookshelf, List<Volume>>
            by bookshelvesViewModel.bookshelves.collectAsState(mapOf())

    Column(
        modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectPinch(onPinchIn = onZoomIn)
        },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        bookshelves.entries.forEach { (_, volumes) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                volumes.forEach { _ ->
                    TinyBook(
                        modifier = Modifier
                            .requiredHeight(30.dp)
                            .requiredWidth(10.dp)
                    )
                }
            }

        }
    }

}

@Composable
private fun TinyBook(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(modifier = modifier) {
        drawRect(color)
    }
}