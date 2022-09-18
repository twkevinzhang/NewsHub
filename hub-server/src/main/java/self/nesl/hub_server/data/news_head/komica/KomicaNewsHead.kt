package self.nesl.hub_server.data.news_head.komica

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.hub_server.data.ImageInfo
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.Text
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_thread.Comment

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
): NewsHead, Comment

fun mockKomicaNewsHead() =
    KomicaNewsHead(
        url = "https://www.google.com",
        title = "How to Google?",
        host = Host.KOMICA,
        createdAt = 0,
        poster = "Zhen Long",
        visits = 0,
        replies = 0,
        readAt = 0,
        content = listOf(
            Text("Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui."),
            ImageInfo("https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png", "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png"),
            Text("This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique."),
        ),
        favorite = null,
        page = 1,
    )