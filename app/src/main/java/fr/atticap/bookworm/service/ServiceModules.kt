package fr.atticap.bookworm.service

import org.koin.dsl.module

fun serviceModule() = module {
    single<BookService> { BookService(get()) }
}