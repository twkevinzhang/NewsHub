package self.nesl.newshub.data.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.newshub.data.Paragraph

@Entity(tableName = "news")
data class News (
    @PrimaryKey val url: String,
    val host: Host,
    val title: String?,
    val createdAt: Long?,
    val poster: String?,
    val visits: Int?,
    val replies: Int?,
    val readAt: Int?,
    val content: List<Paragraph>,
    val favorite: String?,
)

enum class Host {
    KOMICA,
    GAMER,
}