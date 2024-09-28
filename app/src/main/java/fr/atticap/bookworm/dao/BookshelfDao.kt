import androidx.room.Dao
import androidx.room.Query
import fr.atticap.bookworm.model.Book
import fr.atticap.bookworm.model.Bookshelf
import kotlinx.coroutines.flow.Flow

@Dao
interface BookshelfDao {
    @Query("SELECT * FROM bookshelf NATURAL JOIN bookshelf_content NATURAL JOIN book")
    fun getBookshelvesWithBooks(): Flow<Map<Bookshelf, Book>>

    @Query("SELECT * FROM bookshelf")
    fun getAllBookshelves(): Flow<List<Bookshelf>>

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<Book>>
}