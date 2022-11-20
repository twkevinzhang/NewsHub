package self.nesl.hub_server.data.news

import self.nesl.hub_server.data.Paragraph

interface News {
    val threadUrl: String
    val boardUrl: String
    val content: List<Paragraph>
}