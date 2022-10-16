package self.nesl.hub_server.data.news.gamer

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.gamer_api.model.GNews
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news.News

@Entity(tableName = "gamer_news")
data class GamerNews (
    @PrimaryKey override val url: String,
    override val boardUrl: String,
    val title: String?,
    val createdAt: Long?,
    val poster: String?,
    val visits: Int?,
    val replies: Int?,
    val readAt: Int?,
    val content: List<Paragraph>,
    val page: Int,
): News

fun GNews.toGamerPost(page: Int, boardUrl: String) =
    GamerNews(
        boardUrl = boardUrl,
        url = url,
        title = title,
        createdAt = null,
        poster = null,
        replies = null,
        readAt = null,
        content = listOf(Paragraph.Text(preview)),
        page = page,
        visits = null,
    )