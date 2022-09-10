package self.nesl.hub_server.data.news_head.komica

import androidx.room.*

@Dao
interface KomicaNewsHeadDao {

    @Query("SELECT * FROM komica_news where page = :page")
    suspend fun readAll(page: Int): List<KomicaNewsHead>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<KomicaNewsHead>)

    @Query("DELETE FROM komica_news")
    suspend fun clearAll()
}