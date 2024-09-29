package fr.atticap.bookworm.ui

import fr.atticap.bookworm.ui.features.bookshelf.BookshelfViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun bookshelfModules() = module {
    viewModel { BookshelfViewModel(get(), get()) }
}