package self.nesl.hub_server.data.post.gamer

import androidx.room.*

@Dao
interface GamerNewsDao {

    @Query("SELECT * FROM gamer_news where page = :page and boardUrl = :boardUrl")
    suspend fun readAll(boardUrl: String, page: Int): List<GamerNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<GamerNews>)

    @Query("DELETE FROM gamer_news")
    suspend fun clearAll()
}