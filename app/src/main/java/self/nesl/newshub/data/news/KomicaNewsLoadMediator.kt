package self.nesl.newshub.data.news

import android.util.Log
import androidx.paging.LoadType
import androidx.room.withTransaction
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.KPost
import self.nesl.newshub.data.AppDatabase
import self.nesl.newshub.data.toParagraph
import self.nesl.newshub.di.TransactionProvider
import self.nesl.newshub.ui.navigation.TopicNavItems

class KomicaNewsLoadMediator (
    private val newsDao: NewsDao,
    private val newsKeysDao: NewsKeysDao,
    private val transactionProvider: TransactionProvider,
    private val api: KomicaApi,
) {

    suspend fun getEndOfPaginationReachedAfterLoad(loadType: LoadType, page: Int, topicNavItems: TopicNavItems): Boolean {
        val newsList = api.getAllThreadHead(topicNavItems.toKBoard(), page)
        val endOfPaginationReached = newsList.isEmpty()

        transactionProvider.invoke {
            if (loadType == LoadType.REFRESH) {
                newsKeysDao.clear()
                newsDao.clear(Host.KOMICA)
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = newsList.map { news ->
                NewsRemoteKeys(url = news.url, prevKey = prevKey, nextKey = nextKey)
            }
            newsKeysDao.insertAll(keys)
            newsDao.upsertAll(newsList.map { it.toNews() })
        }
        return endOfPaginationReached
    }

    private fun TopicNavItems.toKBoard() =
        when (this) {
            is TopicNavItems.Square -> KBoard.Sora.綜合
            else -> throw NotImplementedError()
        }

    private fun KPost.toNews() =
        News(
            host = Host.KOMICA,
            url = url,
            title = title,
            createdAt = createdAt,
            poster = poster,
            visits = visits,
            replies = replies,
            readAt = readAt,
            content = content.map { it.toParagraph() },
            favorite = null,
        )
}