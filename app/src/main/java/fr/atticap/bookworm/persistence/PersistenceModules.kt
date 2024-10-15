package fr.atticap.bookworm.persistence


import androidx.room.Room
import fr.atticap.bookworm.persistence.dao.BookDao
import fr.atticap.bookworm.persistence.dao.TagDao
import fr.atticap.bookworm.persistence.repository.BookRepository
import fr.atticap.bookworm.persistence.repository.TagRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun persistenceModule() = module {
    single<BookwormDatabase> {
        Room.databaseBuilder(
            get(),
            BookwormDatabase::class.java,
            "bookworm",
        ).build()
    }

    single<BookDao> { get<BookwormDatabase>().bookshelfDao() }
    single<TagDao> { get<BookwormDatabase>().tagDao() }

    singleOf(::BookRepository)
    singleOf(::TagRepository)
}