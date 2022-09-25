package self.nesl.hub_server.data.news_thread

import self.nesl.hub_server.data.news_head.TopNews

interface NewsThread {
    val url: String
    val head: TopNews
    val comments: List<Comment>
}