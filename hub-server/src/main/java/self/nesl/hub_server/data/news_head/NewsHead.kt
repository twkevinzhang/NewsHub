package self.nesl.hub_server.data.news_head

import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph

interface TopNews {
    val url: String
    val host: Host
    val title: String?
    val createdAt: Long?
    val poster: String?
    val visits: Int?
    val replies: Int?
    val readAt: Int?
    val content: List<Paragraph>
    val favorite: String?
}