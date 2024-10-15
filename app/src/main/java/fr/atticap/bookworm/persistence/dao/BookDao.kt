package fr.atticap.bookworm.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import fr.atticap.bookworm.model.Volume
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Dao
interface BookDao {
    @Query("SELECT bookshelf.*, volume.* FROM bookshelf LEFT JOIN bookshelf_content ON bookshelf.id = bookshelfId LEFT JOIN volume ON bookId = volume.id")
    fun getBookshelvesWithBooks(): Flow<Map<Bookshelf, List<Volume>>>

    @Query("SELECT * FROM bookshelf")
    fun getAllBookshelves(): Flow<List<Bookshelf>>

    @Query("SELECT * FROM volume")
    fun getAllBooks(): Flow<List<Volume>>

    @Query("SELECT * FROM volume WHERE id = :id")
    fun getBookById(id: Uuid): Flow<Volume>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(bookshelf: Bookshelf)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(volume: Volume)

    @Transaction
    @Update
    fun insert(bookshelfContent: BookshelfContent)

    @Transaction
    @Delete
    fun delete(bookshelfContent: BookshelfContent)
}