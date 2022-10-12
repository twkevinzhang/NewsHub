package self.nesl.hub_server.data.post.komica

import self.nesl.hub_server.data.post.Topic

interface KomicaPostRepository {
    suspend fun getAllNews(topic: Topic, page: Int): List<KomicaPost>
    suspend fun clearAllNews(topic: Topic)
}