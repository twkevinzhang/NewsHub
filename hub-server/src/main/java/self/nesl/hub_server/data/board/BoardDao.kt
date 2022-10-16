package self.nesl.hub_server.data.board

import androidx.room.*

@Dao
interface BoardDao {

    @Query("SELECT * FROM board")
    suspend fun readAll(): List<Board>

    @Query("SELECT * FROM board WHERE url = :url")
    suspend fun read(url: String): Board?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<Board>)

    @Query("DELETE FROM board")
    suspend fun clearAll()
}