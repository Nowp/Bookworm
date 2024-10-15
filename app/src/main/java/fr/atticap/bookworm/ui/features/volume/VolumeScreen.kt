package fr.atticap.bookworm.ui.features.volume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.None
import arrow.core.Option
import arrow.core.toOption
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.Volume
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
data class VolumeRoute(val id: String)

private const val MOCK_DESCRIPTION =
    "In the mystical kingdom of Eldoria, where magic thrives and ancient prophecies whisper, a young cartographer named Elara discovers a compass crafted from obsidian. This is no ordinary compass, for it points not north, but towards destiny. When a shadowy force threatens to engulf Eldoria in darkness, Elara must embark on a perilous journey, guided by the compass and her own burgeoning magical abilities. Along the way, she'll encounter mythical creatures, cunning sorcerers, and the secrets of a forgotten era. Will she unravel the mysteries of the obsidian compass and save her kingdom before it's too late?\n"

@Composable
fun VolumeScreen(onAddTag: () -> Unit) {
    val detailsViewModel = koinViewModel<VolumeViewModel>()

    val volume: Option<Pair<Volume, List<PositionedTag>>> by detailsViewModel.volume.map { it.toOption() }
        .collectAsState(None)

    volume.onSome { (volume, tags) ->
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            MainInfo(title = volume.title, author = volume.author, year = volume.year)
            Tags(tags = tags.sortedBy(PositionedTag::pos).map(PositionedTag::tag), onAddTag = onAddTag)
            Description(description = MOCK_DESCRIPTION)
        }
    }
}

@Composable
private fun MainInfo(title: String, author: String, year: Int) {
    Text(text = title, style = MaterialTheme.typography.displaySmall)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "by $author", style = MaterialTheme.typography.bodyMedium)
        VerticalDivider(
            modifier = Modifier
                .requiredHeight(MaterialTheme.typography.bodyMedium.fontSize.value.dp),

            )
        Text(text = year.toString())
    }
}

@Composable
private fun Description(modifier: Modifier = Modifier, description: String) {
    Card(modifier) {
        Column(
            modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Description", style = MaterialTheme.typography.headlineMedium)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun Tags(modifier: Modifier = Modifier, tags: List<Tag>, onAddTag: () -> Unit) {
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = tags) {
            InputChip(
                selected = false,
                onClick = {},
                colors = InputChipDefaults.inputChipColors(
                    containerColor = it.color
                ),
                label = { Text(text = it.name) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null
                    )
                }
            )
        }
        item {
            IconButton(onClick = onAddTag) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    }
}