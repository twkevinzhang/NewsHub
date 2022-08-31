package self.nesl.newshub.data.news

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_remote_keys")
data class NewsRemoteKeys(
    @PrimaryKey val url: String,
    val prevKey: Int?,
    val nextKey: Int?,
)