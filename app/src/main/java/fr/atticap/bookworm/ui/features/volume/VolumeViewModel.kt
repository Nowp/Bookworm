package fr.atticap.bookworm.ui.features.volume

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

class VolumeViewModel(
    savedStateHandle: SavedStateHandle,
    bookRepository: BookRepository
) : ViewModel() {
    private val volumeDetails = savedStateHandle.toRoute<VolumeRoute>()
    val volume: Flow<Volume> = bookRepository.getBookById(Uuid.parse(volumeDetails.id))
}