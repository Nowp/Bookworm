package fr.atticap.bookworm.persistence.repository

import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Volume
import fr.atticap.bookworm.persistence.dao.BookDao
import fr.atticap.bookworm.persistence.dao.TagDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class BookRepository(private val bookDao: BookDao, private val tagDao: TagDao) {
    val books: Flow<List<Volume>> = bookDao.getAllBooks()
    val bookshelves: Flow<Map<Bookshelf, List<Volume>>> = bookDao.getBookshelvesWithBooks()

    fun getBookById(id: Uuid): Flow<Volume> = bookDao.getBookById(id)

    fun getBookWithTags(id: Uuid): Flow<Pair<Volume, List<PositionedTag>>> =
        combine(bookDao.getBookById(id), tagDao.getAllTagsOfVolume(id), ::Pair)
}