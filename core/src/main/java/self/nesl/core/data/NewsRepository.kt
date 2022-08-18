package self.nesl.core.data

import self.nesl.core.domain.News

interface NewsRepository {
    suspend fun getNews(newsId: String): News
    suspend fun getAllNewsNearby(x: Float, y: Float, page: Int, size: Int): List<News>
    suspend fun getAllNews(page: Int, size: Int): List<News>
}