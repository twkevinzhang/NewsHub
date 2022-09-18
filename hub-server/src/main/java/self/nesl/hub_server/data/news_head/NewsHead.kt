package self.nesl.hub_server.data.news_head

import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph

interface NewsHead {
    val url: String
    val host: Host
    open val title: String?
    open val createdAt: Long?
    open val poster: String?
    open val visits: Int?
    open val replies: Int?
    open val readAt: Int?
    open val content: List<Paragraph>
    open val favorite: String?
}