package self.nesl.newshub.framework.data

import android.content.Context
import self.nesl.core.data.NewsDataSource

class NewsDataSourceImpl(
    private val context: Context,
): NewsDataSource {
    private val newsDao = AppDatabase.getInstance(context).newsDao()

    override suspend fun read(newsId: String) =
        newsDao.read(newsId)

    override suspend fun readAllNearby(x: Float, y: Float, page: Int, size: Int) =
        newsDao.readAllNearby(x, y, page, size)

    override suspend fun readAll(page: Int, size: Int) =
        newsDao.readAll(page, size)
}