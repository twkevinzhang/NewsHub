package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.TopNews
import self.nesl.hub_server.data.news_head.Topic
import self.nesl.hub_server.data.news_head.komica.KomicaTopNewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopNewsUseCase @Inject constructor(
    private val komicaTopNewsRepository: KomicaTopNewsRepository,
) {
    suspend fun getAllTopNews(
        topic: Topic,
        page: Map<Host, Int>,
    ): List<TopNews> =
        ArrayList<TopNews>().apply {
            if (page.containsKey(Host.KOMICA)) {
                addAll(komicaTopNewsRepository.getAllTopNews(topic, page[Host.KOMICA]!! - 1))
            }
        }

    suspend fun clearAllTopNews(topic: Topic) {
        komicaTopNewsRepository.clearAllTopNews(topic)
    }
}