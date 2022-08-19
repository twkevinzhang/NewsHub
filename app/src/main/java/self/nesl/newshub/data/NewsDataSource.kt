package self.nesl.newshub.data

interface NewsDataSource {
    suspend fun read(newsId: String): News
    suspend fun readAllNearby(x: Float, y: Float, page: Int, size: Int): List<News>
	suspend fun readAll(page: Int, size: Int): List<News>
}