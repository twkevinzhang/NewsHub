package self.nesl.hub_server.data.news_head.komica

import androidx.room.*

@Dao
interface KomicaNewsHeadDao {

    @Query("SELECT * FROM komica_news where page = :page")
    fun readAll(page: Int): List<KomicaNewsHead>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<KomicaNewsHead>)
}