package self.nesl.hub_server.data.news_head.komica

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead

@Entity(tableName = "komica_news")
data class KomicaNewsHead (
    @PrimaryKey override val url: String,
    override val host: Host,
    override val title: String?,
    override val createdAt: Long?,
    override val poster: String?,
    override val visits: Int?,
    override val replies: Int?,
    override val readAt: Int?,
    override val content: List<Paragraph>,
    override val favorite: String?,
    val page: Int,
): NewsHead(
    url = url,
    host = host,
    title = title,
    createdAt = createdAt,
    poster = poster,
    visits = visits,
    replies = replies,
    readAt = readAt,
    content = content,
    favorite = favorite,
)