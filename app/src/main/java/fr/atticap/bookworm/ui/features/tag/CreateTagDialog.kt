package fr.atticap.bookworm.ui.features.tag

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.Ior
import arrow.core.getOrElse
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateTagDialog() {
    val tagViewModel = koinViewModel<TagViewModel>()
    val tagFormState: Ior<List<TagFormValidation>, TagForm> by tagViewModel.tagFormState.collectAsState()
    val tagForm: TagForm = tagFormState.getOrElse {
        throw IllegalStateException("Invalid tag form state $it")
    }

    Log.d("CreateTagDialog", "tagForm: $tagForm")
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Create Tag", style = MaterialTheme.typography.titleMedium)
            TextField(value = tagForm.name, onValueChange = tagViewModel::setName)
        }
    }
}