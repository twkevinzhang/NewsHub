package self.nesl.hub_server.data.news_thread.komica

import self.nesl.hub_server.data.news_head.TopNews
import self.nesl.hub_server.data.news_thread.RePost
import self.nesl.hub_server.data.news_thread.NewsThread

data class KomicaNewsThread (
    override val url: String,
    override val post: TopNews,
    override val rePost: List<RePost>,
): NewsThread