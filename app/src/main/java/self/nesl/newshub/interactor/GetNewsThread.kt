package self.nesl.newshub.interactor

import self.nesl.hub_server.data.news_thread.NewsThread
import self.nesl.hub_server.interactor.NewsThreadUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNewsThread @Inject constructor(
    private val newsThreadUseCase: NewsThreadUseCase,
) {
    suspend fun invoke(url: String): NewsThread {
        return newsThreadUseCase.getNewsThread(url)
    }
}