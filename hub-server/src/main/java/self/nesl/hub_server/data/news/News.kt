package self.nesl.hub_server.data.news

import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.Host

interface News {
    val url: String
    val host: Host
    val title: String?
    val createdAt: Long?
    val poster: String?
    val visits: Int?
    val replies: Int?
    val readAt: Int?
    val content: List<Paragraph>
}