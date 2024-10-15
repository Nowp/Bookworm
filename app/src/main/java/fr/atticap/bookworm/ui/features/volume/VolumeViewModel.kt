package fr.atticap.bookworm.ui.features.volume

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.service.BookService
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

class VolumeViewModel(
    savedStateHandle: SavedStateHandle,
    bookRepository: BookRepository,
    private val bookService: BookService
) : ViewModel() {
    private val volumeDetails = savedStateHandle.toRoute<VolumeRoute>()
    val volume: Flow<Pair<Volume, List<PositionedTag>>> =
        bookRepository.getBookWithTags(Uuid.parse(volumeDetails.id))
}