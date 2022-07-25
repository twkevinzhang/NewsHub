package self.nesl.core.data

import self.nesl.core.domain.News

interface NewsDataSource {
    suspend fun read(newsId: String): News
    suspend fun readAllNearby(x: Float, y: Float, page: Int, size: Int): List<News>
	suspend fun readAll(page: Int, size: Int): List<News>
}