package fr.atticap.bookworm.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import fr.atticap.bookworm.model.BookshelfContent
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Dao
interface BookDao {
    @Query("SELECT bookshelf.*, book.* FROM bookshelf LEFT JOIN bookshelf_content ON bookshelf.id = bookshelfId LEFT JOIN book ON bookId = book.id")
    fun getBookshelvesWithBooks(): Flow<Map<Bookshelf, List<Book>>>

    @Query("SELECT * FROM bookshelf")
    fun getAllBookshelves(): Flow<List<Bookshelf>>

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBookById(id: Uuid): Flow<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateBookshelf(bookshelf: Bookshelf)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBookshelf(book: Book)

    @Transaction
    @Insert
    fun insertBookshelfContent(bookshelfContent: BookshelfContent)

    @Transaction
    @Delete
    fun deleteBookshelfContent(bookshelfContent: BookshelfContent)
}