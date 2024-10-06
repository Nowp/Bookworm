package fr.atticap.bookworm.ui

import fr.atticap.bookworm.ui.features.bookshelf.BookshelvesViewModel
import fr.atticap.bookworm.ui.features.volume.VolumeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun bookshelfModules() = module {
    viewModelOf(::BookshelvesViewModel)
    viewModelOf(::VolumeViewModel)
}