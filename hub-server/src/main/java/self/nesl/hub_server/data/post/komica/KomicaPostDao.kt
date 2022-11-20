package self.nesl.hub_server.data.post.komica

import androidx.room.*

@Dao
interface KomicaPostDao {

    @Query("SELECT * FROM komica_news where page = :page and boardUrl = :boardUrl")
    suspend fun readAllNews(boardUrl: String, page: Int): List<KomicaPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<KomicaPost>)

    @Query("DELETE FROM komica_news")
    suspend fun clearAll()

    @Query("SELECT * FROM komica_news where threadUrl = :threadUrl")
    suspend fun readNews(threadUrl: String): KomicaPost

    @Query("SELECT * FROM komica_news where threadUrl = :threadUrl and id = :headPostId")
    suspend fun readByRePostId(threadUrl: String, headPostId: String): KomicaPost

    @Query("SELECT * FROM komica_news where threadUrl = :threadUrl")
    suspend fun readAllByThreadUrl(threadUrl: String): List<KomicaPost>

    @Query("SELECT * FROM komica_news where page = :page and threadUrl = :threadUrl and content like '%{\"id\":\"' || :headPostId || '\",\"type\":\"REPLY_TO\"}%'")
    suspend fun readAllByRePostId(threadUrl: String, headPostId: String, page: Int): List<KomicaPost>

    @Query("DELETE FROM komica_news WHERE threadUrl = :threadUrl")
    suspend fun clearAllByThreadUrl(threadUrl: String)
}