package self.nesl.hub_server.data.post.gamer

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news.News
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.toParagraph

@Entity(tableName = "gamer_news")
data class GamerNews (
    @PrimaryKey override val url: String,
    override val boardUrl: String,
    override val title: String?,
    override val createdAt: Long?,
    override val poster: String?,
    override val visits: Int?,
    override val replies: Int?,
    override val readAt: Int?,
    override val content: List<Paragraph>,
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