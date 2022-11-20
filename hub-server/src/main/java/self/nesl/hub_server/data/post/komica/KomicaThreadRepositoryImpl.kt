package self.nesl.hub_server.data.post.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.toKBoard
import self.nesl.hub_server.data.news.News
import self.nesl.hub_server.data.post.ThreadRepository
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class KomicaThreadRepositoryImpl @Inject constructor(
    private val dao: KomicaPostDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ThreadRepository<KomicaPost> {

    override suspend fun getPostThread(threadUrl: String, page: Int, board: Board): List<KomicaPost> = withContext(ioDispatcher) {
        if (page > 1) return@withContext emptyList()
        val news = dao.readNews(threadUrl) as? News
        if (news != null) {
            val thread = dao.readAllByThreadUrl(news.threadUrl)
            val isEmpty = thread.size <= 1
            if (!isEmpty) {
                return@withContext thread
            }
        }
        try {
            val req = api.getRequestBuilder(board.toKBoard())
                .url(threadUrl)
                .build()
            val remote = api.getAllPost(req).map { it.toKomicaPost(page, board.url, threadUrl) }
            transactionProvider.invoke {
                dao.upsertAll(remote)
            }
            remote
        } catch (e: Exception) {
            Log.e("KomicaPostRepo", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getRePostThread(
        threadUrl: String,
        rePostId: String,
        page: Int,
    ): List<KomicaPost> = withContext(ioDispatcher) {
        val head = dao.readByRePostId(threadUrl, rePostId)
        val sub = dao.readAllByRePostId(threadUrl, rePostId, page)
        listOf(head).plus(sub)
    }

    override suspend fun removePostThread(threadUrl: String) = withContext(ioDispatcher) {
        dao.clearAllByThreadUrl(threadUrl)
    }
}