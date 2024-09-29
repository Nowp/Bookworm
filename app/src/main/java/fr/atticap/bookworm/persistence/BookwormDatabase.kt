package fr.atticap.bookworm.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.persistence.converters.UuidConverter
import fr.atticap.bookworm.persistence.dao.BookDao

@Database(version = 1, entities = [Bookshelf::class, Book::class, BookshelfContent::class], exportSchema = true)
@TypeConverters(UuidConverter::class)
abstract class BookwormDatabase: RoomDatabase() {
    abstract fun bookshelfDao(): BookDao
}