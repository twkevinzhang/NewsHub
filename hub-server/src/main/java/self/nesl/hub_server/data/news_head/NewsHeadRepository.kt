package self.nesl.hub_server.data.news_head

interface NewsHeadRepository {
    suspend fun getAllNewsHead(topic: Topic, page: Int): List<NewsHead>
}