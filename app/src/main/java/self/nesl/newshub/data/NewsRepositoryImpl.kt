package self.nesl.newshub.data

class NewsRepositoryImpl(
    private val newsDataSource: NewsDataSource,
): NewsRepository {
    override suspend fun getNews(newsId: String) = newsDataSource.read(newsId)
    override suspend fun getAllNewsNearby(x: Float, y: Float, page: Int, size: Int) = newsDataSource.readAllNearby(x, y, page, size)
    override suspend fun getAllNews(page: Int, size: Int) = newsDataSource.readAll(page, size)
}