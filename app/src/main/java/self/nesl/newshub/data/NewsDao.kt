package self.nesl.newshub.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM news where newsId = :newsId")
    suspend fun read(newsId: String): News

    suspend fun readAllNearby(x: Float, y: Float, page: Int, size: Int): List<News>

    suspend fun readAll(page: Int, size: Int): List<News>

    @Query("SELECT * FROM news where readAt is not null")
    suspend fun readAllHistory(): List<News>

    @Query("SELECT * FROM news where favorite = :favorite")
    suspend fun readAllFavorite(favorite: String): List<News>
}