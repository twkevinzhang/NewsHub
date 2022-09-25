package self.nesl.hub_server.data.news_head.komica

import androidx.room.*

@Dao
interface KomicaTopNewsDao {

    @Query("SELECT * FROM komica_news where page = :page")
    suspend fun readAll(page: Int): List<KomicaTopNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<KomicaTopNews>)

    @Query("DELETE FROM komica_news")
    suspend fun clearAll()
}