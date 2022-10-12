package self.nesl.hub_server.data.post.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.post.*
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.hub_server.toKomicaPost
import javax.inject.Inject

class KomicaPostRepositoryImpl @Inject constructor(
    private val dao: KomicaPostDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaPostRepository {

    override suspend fun getAllNews(topic: Topic, page: Int): List<KomicaPost> = withContext(ioDispatcher) {
        val news = dao.readAll(page)
        if (news.isNotEmpty()) {
            news
        } else {
            try {
                val remote = api.getAllThreadHead(topic.toKBoard(), page.takeIf { it != 0 }).map { it.toKomicaPost(page) }
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("KomicaPostRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    private fun Topic.toKBoard() =
        when (this) {
            Topic.Square -> KBoard.Sora.綜合
            else -> throw NotImplementedError()
        }

    override suspend fun clearAllNews(topic: Topic) = withContext(ioDispatcher) {
        dao.clearAll()
    }
}