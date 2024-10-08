package fr.atticap.bookworm.ui.features.volume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Card
import androidx.compose.material3.InputChip
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
import fr.atticap.bookworm.model.Volume
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
data class VolumeRoute(val id: String)

private const val MOCK_DESCRIPTION =
    "In the mystical kingdom of Eldoria, where magic thrives and ancient prophecies whisper, a young cartographer named Elara discovers a compass crafted from obsidian. This is no ordinary compass, for it points not north, but towards destiny. When a shadowy force threatens to engulf Eldoria in darkness, Elara must embark on a perilous journey, guided by the compass and her own burgeoning magical abilities. Along the way, she'll encounter mythical creatures, cunning sorcerers, and the secrets of a forgotten era. Will she unravel the mysteries of the obsidian compass and save her kingdom before it's too late?\n"

@Composable
fun VolumeScreen() {
    val detailsViewModel = koinViewModel<VolumeViewModel>()

    val volume: Option<Volume> by detailsViewModel.volume.map { it.toOption() }.collectAsState(None)

    volume.onSome {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            MainInfo(title = it.title, author = it.author, year = it.year)
            Description(description = MOCK_DESCRIPTION)
            Tags()
        }
    }
}

@Composable
private fun MainInfo(title: String, author: String, year: Int) {
    Text(text = title, style = MaterialTheme.typography.displayMedium)
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
    Card(modifier.padding(all = 4.dp)) {
        Text(text = "Description", style = MaterialTheme.typography.headlineMedium)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }

}

@Composable
private fun Tags(modifier: Modifier = Modifier) {
    Row {
        InputChip(selected = true, onClick = {}, label = { Text(text = "Chip") })
    }
}