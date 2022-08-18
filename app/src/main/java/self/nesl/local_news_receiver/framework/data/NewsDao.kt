package self.nesl.local_news_receiver.framework.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import self.nesl.core.domain.News

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