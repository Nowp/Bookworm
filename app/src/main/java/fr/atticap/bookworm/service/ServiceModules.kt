package fr.atticap.bookworm.service

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun serviceModule() = module {
    singleOf(::BookService)
}