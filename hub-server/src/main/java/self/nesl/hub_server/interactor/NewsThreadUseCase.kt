package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.news_thread.NewsThread
import self.nesl.hub_server.data.news_thread.komica.KomicaNewsThreadRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsThreadUseCase @Inject constructor(
    private val komicaNewsThreadRepository: KomicaNewsThreadRepository,
) {
    suspend fun getNewsThread(
        url: String,
    ): NewsThread {
        return komicaNewsThreadRepository.getNewsThread(url)
    }
}