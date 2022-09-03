package self.nesl.newshub.data.news

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE host not in (:exclude)")
    fun readAll(exclude: List<Host>): PagingSource<Int, News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<News>)

    @Query("DELETE FROM news WHERE host = :host")
    suspend fun clear(host: Host)
}