package self.nesl.hub_server.data.post.komica

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.model.KPost

@Entity(tableName = "komica_news")
data class KomicaPost (
    @PrimaryKey override val url: String,
    override val id: String,
    override val boardUrl: String,
    override val title: String?,
    override val createdAt: Long?,
    override val poster: String?,
    override val visits: Int?,
    override val replies: Int?,
    override val readAt: Int?,
    override val content: List<Paragraph>,
    val page: Int,
): Post

fun KPost.toKomicaPost(page: Int, boardUrl: String) =
    KomicaPost(
        boardUrl = boardUrl,
        url = url,
        title = title,
        createdAt = createdAt,
        poster = poster,
        visits = visits,
        replies = replies,
        readAt = readAt,
        content = content.map { it.toParagraph() },
        page = page,
        id = id,
    )

fun mockKomicaPost() =
    KomicaPost(
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
        id = "1",
    )