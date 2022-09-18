package self.nesl.hub_server.data.news_head.komica

import self.nesl.hub_server.data.news_head.Topic

interface KomicaNewsHeadRepository {
    suspend fun getAllNewsHead(topic: Topic, page: Int): List<KomicaNewsHead>
    suspend fun clearAllNewsHead(topic: Topic)
}