package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.Topic
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHeadHeadRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsHeadUseCase @Inject constructor(
    private val komicaNewsHeadRepository: KomicaNewsHeadHeadRepository,
) {
    suspend fun getAllNewsHead(
        topic: Topic,
        page: Map<Host, Int>,
    ): List<NewsHead> =
        ArrayList<NewsHead>().apply {
            if (page.containsKey(Host.KOMICA)) {
                addAll(komicaNewsHeadRepository.getAllNewsHead(topic, page[Host.KOMICA]!! - 1))
            }
        }

    suspend fun clearAllNewsHead(topic: Topic) {
        komicaNewsHeadRepository.clearAllNewsHead(topic)
    }
}