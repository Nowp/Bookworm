package fr.atticap.bookworm.service

import android.util.Log
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.model.VolumeTag
import fr.atticap.bookworm.persistence.dao.BookDao
import fr.atticap.bookworm.persistence.dao.TagDao
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class BookService(private val bookDao: BookDao, private val tagDao: TagDao) {

    fun addToBookshelf(volume: Volume, bookshelf: Bookshelf) {
        Log.d("BookService", "Adding book $volume to bookshelf $bookshelf")
        bookDao.insert(BookshelfContent(bookshelf.id, volume.id))
    }

    suspend fun addTagToVolume(volume: Volume, tag: Tag, pos: Int) {
        Log.d("BookService", "Adding tag $tag to volume $volume")

        tagDao.update(tag)
        tagDao.insertVolumeTag(VolumeTag(volume.id, tag.id, pos))
    }

    suspend fun updateBookshelf(bookshelf: Bookshelf) {
        Log.d("BookService", "Updating bookshelf $bookshelf")
        bookDao.update(bookshelf)
    }

    suspend fun updateVolume(volume: Volume) {
        Log.d("BookService", "Updating volume $volume")
        bookDao.update(volume)
    }
}