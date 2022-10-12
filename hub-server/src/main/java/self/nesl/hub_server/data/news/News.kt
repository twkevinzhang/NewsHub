package self.nesl.hub_server.data.post

import self.nesl.hub_server.data.Paragraph

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
    val favorite: String?
}