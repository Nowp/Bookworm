package fr.atticap.bookworm.ui.features.volume

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.atticap.bookworm.model.Volume
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Serializable
data class VolumeRoute(val id: String)

@Composable
fun VolumeScreen() {
    val detailsViewModel = koinViewModel<VolumeViewModel>()

    val volume: Volume? by detailsViewModel.volume.collectAsState(null)

    if (volume != null) {
        Text(text = volume!!.id.toString())
    }
}