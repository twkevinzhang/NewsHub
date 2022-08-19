package self.nesl.newshub.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val ioDispatcher: CoroutineDispatcher,
): NewsDataSource {

    override suspend fun read(newsId: String) = withContext(ioDispatcher) {
        newsDao.read(newsId)
    }

    override suspend fun readAllNearby(x: Float, y: Float, page: Int, size: Int) = withContext(ioDispatcher) {
        newsDao.readAllNearby(x, y, page, size)
    }

    override suspend fun readAll(page: Int, size: Int) = withContext(ioDispatcher) {
        newsDao.readAll(page, size)
    }
}