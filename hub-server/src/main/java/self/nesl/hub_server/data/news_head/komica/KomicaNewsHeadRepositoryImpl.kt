package self.nesl.hub_server.data.news_head.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.news_head.*
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.hub_server.toKomicaTopNews
import javax.inject.Inject

class KomicaTopNewsRepositoryImpl @Inject constructor(
    private val dao: KomicaTopNewsDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaTopNewsRepository {

    override suspend fun getAllTopNews(topic: Topic, page: Int): List<KomicaTopNews> = withContext(ioDispatcher) {
        val news = dao.readAll(page)
        if (news.isNotEmpty()) {
            news
        } else {
            try {
                val remote = api.getAllThreadHead(topic.toKBoard(), page.takeIf { it != 0 }).map { it.toKomicaTopNews(page) }
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("KomicaTopNewsRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    private fun Topic.toKBoard() =
        when (this) {
            Topic.Square -> KBoard.Sora.綜合
            else -> throw NotImplementedError()
        }

    override suspend fun clearAllTopNews(topic: Topic) = withContext(ioDispatcher) {
        dao.clearAll()
    }
}