package self.nesl.core.data

import kotlinx.coroutines.CoroutineScope

class NewsRepository(
    private val newsDataSource: NewsDataSource,
    private val externalScope: CoroutineScope,
) {
    suspend fun getNews(newsId: String) = newsDataSource.read(newsId)
    suspend fun getAllNewsNearby(centerPoint: Float, range: Float, page: Int, size: Int) = newsDataSource.readAllNearby(centerPoint, range, page, size)
    suspend fun getAllNews(page: Int, size: Int) = newsDataSource.readAll(page, size)
}