package self.nesl.newshub.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.komica_api.model.Paragraph

@Entity(tableName = "news")
data class News (
    @PrimaryKey(autoGenerate = true) val newsId: Int = 0,
    @ColumnInfo(name = "host") val host: String? = null,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "visits") val visits: Int,
    @ColumnInfo(name = "replies") val replies: Int,
    @ColumnInfo(name = "readAt") val readAt: Int? = null,
    @ColumnInfo(name = "content") val content: List<Paragraph>,
    @ColumnInfo(name = "favorite") val favorite: String? = null,
)
