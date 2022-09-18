package self.nesl.hub_server.data.news_thread.komica

import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_thread.Comment
import self.nesl.hub_server.data.news_thread.NewsThread

data class KomicaNewsThread (
    override val url: String,
    override val head: NewsHead,
    override val comments: List<Comment>,
): NewsThread