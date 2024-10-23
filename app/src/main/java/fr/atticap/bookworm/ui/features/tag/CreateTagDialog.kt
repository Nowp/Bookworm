package fr.atticap.bookworm.ui.features.tag

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.toOption
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.ui.theme.TagColorPalette
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.Uuid

@Composable
fun CreateTagDialog(title: String, action: String, onDismiss: (Uuid) -> Unit) {
    val tagViewModel = koinViewModel<TagViewModel>()
    val tag: Option<Tag> by tagViewModel.tag.collectAsState(initial = None)
    val result: DialogResult by tagViewModel.dialogResult.collectAsState()

    LaunchedEffect(tag) {
        tag.fold(
            ifSome = {
                tagViewModel.name = it.name
                tagViewModel.color = it.color
            },
            ifEmpty = {}
        )
    }

    LaunchedEffect(result) {
        if (result is DialogResult.Saved) onDismiss((result as DialogResult.Saved).tag.id)
    }

    val form: Either<List<TagFormValidation>, TagForm> by tagViewModel.tagFormState.collectAsState()
    val errors = form.leftOrNull().toOption().getOrElse { emptyList() }

    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)

            TagName(tagViewModel.name, tagViewModel::name::set)
            TagColor(tagViewModel.color, tagViewModel::color::set)

            when (result) {
                DialogResult.None -> Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        tagViewModel.createTag()
                    }, enabled = errors.isEmpty() && result == DialogResult.None
                ) {
                    Text(action)
                }
                DialogResult.Pending, is DialogResult.Saved  -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

        }
    }
}

@Composable
private fun TagName(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text(text = "Name") }
    )
}

@Composable
private fun TagColor(color: Color, onColorChange: (Color) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(TagColorPalette) { col ->
            Surface(
                modifier = Modifier
                    .size(64.dp)
                    .run {
                        if (col == color) border(
                            4.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(corner = CornerSize(8.dp))
                        ) else this
                    }
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                color = col,
                onClick = { onColorChange(col) }
            ) { }
        }
    }

}