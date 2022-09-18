package self.nesl.hub_server.data.news_thread

import self.nesl.hub_server.data.news_head.NewsHead

interface NewsThread {
    val url: String
    val head: NewsHead
    val comments: List<Comment>
}