package self.nesl.newshub.data.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import self.nesl.newshub.data.news.NewsRemoteKeys

@Dao
interface NewsKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(remoteKey: List<NewsRemoteKeys>)

    @Query("SELECT * FROM news_remote_keys WHERE url = :url")
    suspend fun remoteKeysUrl(url: String): NewsRemoteKeys?

    @Query("DELETE FROM news_remote_keys")
    suspend fun clear()
}