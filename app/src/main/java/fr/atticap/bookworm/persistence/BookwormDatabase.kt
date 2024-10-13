package fr.atticap.bookworm.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.model.VolumeTag
import fr.atticap.bookworm.persistence.converters.ColorConverter
import fr.atticap.bookworm.persistence.converters.UuidConverter
import fr.atticap.bookworm.persistence.dao.BookDao
import fr.atticap.bookworm.persistence.dao.TagDao

@Database(
    version = 1,
    entities = [Bookshelf::class, Volume::class, BookshelfContent::class, Tag::class, VolumeTag::class],
    exportSchema = true
)
@TypeConverters(UuidConverter::class, ColorConverter::class)
abstract class BookwormDatabase : RoomDatabase() {
    abstract fun bookshelfDao(): BookDao
    abstract fun tagDao(): TagDao
}