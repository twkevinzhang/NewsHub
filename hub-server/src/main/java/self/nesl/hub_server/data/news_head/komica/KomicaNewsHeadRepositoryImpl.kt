package self.nesl.hub_server.data.news_head.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.news_head.*
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.hub_server.toKomicaNewsHead
import javax.inject.Inject

class KomicaNewsHeadRepositoryImpl @Inject constructor(
    private val dao: KomicaNewsHeadDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaNewsHeadRepository {

    override suspend fun getAllNewsHead(topic: Topic, page: Int): List<KomicaNewsHead> = withContext(ioDispatcher) {
        val news = dao.readAll(page)
        if (news.isNotEmpty()) {
            news
        } else {
            try {
                val remote = api.getAllThreadHead(topic.toKBoard(), page.takeIf { it != 0 }).map { it.toKomicaNewsHead(page) }
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("KomicaNewsHeadRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    private fun Topic.toKBoard() =
        when (this) {
            Topic.Square -> KBoard.Sora.綜合
            else -> throw NotImplementedError()
        }

    override suspend fun clearAllNewsHead(topic: Topic) = withContext(ioDispatcher) {
        dao.clearAll()
    }
}