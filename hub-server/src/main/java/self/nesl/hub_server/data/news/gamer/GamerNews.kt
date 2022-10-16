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

fun mockGamerNews() =
    GamerNews(
        url = "https://gaia.komica.org/00/pixmicat.php?res=29683783",
        title = "How to Google?",
        boardUrl = "https://gaia.komica.org/00",
        createdAt = 0,
        poster = "Zhen Long",
        visits = 0,
        replies = 0,
        readAt = 0,
        content = listOf(
            Paragraph.Text("Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui."),
            Paragraph.ImageInfo(
                "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png",
                "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png"
            ),
            Paragraph.Text("This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique."),
        ),
        page = 1,
    )