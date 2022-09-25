package self.nesl.hub_server.data.news_head.komica

import self.nesl.hub_server.data.news_head.Topic

interface KomicaTopNewsRepository {
    suspend fun getAllTopNews(topic: Topic, page: Int): List<KomicaTopNews>
    suspend fun clearAllTopNews(topic: Topic)
}