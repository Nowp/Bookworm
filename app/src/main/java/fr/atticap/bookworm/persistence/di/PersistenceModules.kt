package fr.atticap.bookworm.persistence.di


import androidx.room.Room
import fr.atticap.bookworm.persistence.BookwormDatabase
import fr.atticap.bookworm.persistence.dao.BookDao
import fr.atticap.bookworm.persistence.repository.BookRepository
import org.koin.dsl.module

fun persistenceModule() = module {
    single<BookwormDatabase> {
        Room.databaseBuilder(
            get(),
            BookwormDatabase::class.java,
            "bookworm"
        ).fallbackToDestructiveMigrationFrom().build()
    }

    single<BookDao> {
        get<BookwormDatabase>().bookshelfDao()
    }

    single<BookRepository> { BookRepository(get()) }
}