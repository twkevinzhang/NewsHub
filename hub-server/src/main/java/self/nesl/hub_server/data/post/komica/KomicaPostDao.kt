package self.nesl.hub_server.data.post.komica

import androidx.room.*

@Dao
interface KomicaPostDao {

    @Query("SELECT * FROM komica_news where page = :page and boardUrl = :boardUrl")
    suspend fun readAll(boardUrl: String, page: Int): List<KomicaPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<KomicaPost>)

    @Query("DELETE FROM komica_news")
    suspend fun clearAll()
}