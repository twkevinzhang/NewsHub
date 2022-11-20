package self.nesl.hub_server.data.post.gamer

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.news.gamer.GamerNewsDao
import self.nesl.hub_server.data.post.ThreadRepository
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class GamerThreadRepositoryImpl @Inject constructor(
    private val newsDao: GamerNewsDao,
    private val postDao: GamerPostDao,
    private val api: GamerApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ThreadRepository<GamerPost> {

    override suspend fun getPostThread(threadUrl: String, page: Int, board: Board) = withContext(ioDispatcher) {
        val news = newsDao.readNews(threadUrl)
        val thread = postDao.readAllByThreadUrl(news.threadUrl)
        val isEmpty = thread.size == 1
        if (isEmpty) {
            try {
                val req = api.getRequestBuilder()
                    .url(threadUrl)
                    .setPageReq(page)
                    .build()
                val remote = api.getAllPost(req).map { it.toGamerPost(page, threadUrl) }
                transactionProvider.invoke {
                    postDao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("GamerPostRepo", e.stackTraceToString())
                emptyList()
            }
        } else {
            thread
        }
    }

    override suspend fun getRePostThread(
        threadUrl: String,
        rePostId: String,
        page: Int,
    ): List<GamerPost> = withContext(ioDispatcher) {
        val head = postDao.readByRePostId(threadUrl, rePostId)
        val sub = postDao.readAllByRePostId(threadUrl, rePostId, page)
        listOf(head).plus(sub)
    }

    override suspend fun removePostThread(threadUrl: String) = withContext(ioDispatcher) {
        postDao.clearAllByThreadUrl(threadUrl)
    }
}