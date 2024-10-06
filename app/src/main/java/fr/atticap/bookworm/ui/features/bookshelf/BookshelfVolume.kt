package fr.atticap.bookworm.ui.features.bookshelf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fr.atticap.bookworm.ui.theme.MediumBrown

/**
 * Book displayed on the book shelf
 */
@Composable
fun Volume(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    title: String = "Title"
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(title),
        style = TextStyle(
            color = MediumBrown,
            fontSize = 26.sp
        )
    )

    Canvas(modifier.fillMaxSize()) {
        drawRect(color)
        drawText(textLayoutResult)
    }
}


@Preview
@Composable
private fun PreviewBook() {
    Volume()
}