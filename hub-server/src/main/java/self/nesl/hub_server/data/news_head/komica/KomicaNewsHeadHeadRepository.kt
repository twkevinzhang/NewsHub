package self.nesl.hub_server.data.news_head.komica

import self.nesl.hub_server.data.news_head.NewsHeadRepository
import self.nesl.hub_server.data.news_head.Topic

interface KomicaNewsHeadHeadRepository: NewsHeadRepository {
    override suspend fun getAllNewsHead(topic: Topic, page: Int): List<KomicaNewsHead>
}