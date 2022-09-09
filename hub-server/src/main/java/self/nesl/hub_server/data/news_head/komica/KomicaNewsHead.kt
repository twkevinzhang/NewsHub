package self.nesl.hub_server.data.news_head.komica

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.ParagraphType
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

fun mockKomicaNewsHead() =
    NewsHead(
        url = "https://www.google.com",
        title = "How to Google?",
        host = Host.KOMICA,
        createdAt = 0,
        poster = "Zhen Long",
        visits = 0,
        replies = 0,
        readAt = 0,
        content = listOf(
            Paragraph("Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.", ParagraphType.TEXT),
            Paragraph("https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png", ParagraphType.IMAGE),
            Paragraph("This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.", ParagraphType.TEXT),
        ),
        favorite = null,
    )