package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.post.Host
import self.nesl.hub_server.data.post.News
import self.nesl.hub_server.data.post.Topic
import self.nesl.hub_server.data.post.komica.KomicaPostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCase @Inject constructor(
    private val komicaPostRepository: KomicaPostRepository,
) {
    suspend fun getAllNews(
        topic: Topic,
        page: Map<Host, Int>,
    ): List<News> =
        ArrayList<News>().apply {
            if (page.containsKey(Host.KOMICA)) {
                addAll(komicaPostRepository.getAllNews(topic, page[Host.KOMICA]!! - 1))
            }
        }

    suspend fun clearAllNews(topic: Topic) {
        komicaPostRepository.clearAllNews(topic)
    }
}