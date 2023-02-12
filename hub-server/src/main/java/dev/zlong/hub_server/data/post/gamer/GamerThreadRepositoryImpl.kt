package dev.zlong.hub_server.data.post.gamer

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.zlong.gamer_api.GamerApi
import dev.zlong.hub_server.data.board.Board
import dev.zlong.hub_server.data.news.News
import dev.zlong.hub_server.data.news.gamer.GamerNewsDao
import dev.zlong.hub_server.data.post.ThreadRepository
import dev.zlong.hub_server.di.TransactionProvider
import dev.zlong.newshub.di.IoDispatcher
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject

class GamerThreadRepositoryImpl @Inject constructor(
    private val newsDao: GamerNewsDao,
    private val postDao: GamerPostDao,
    private val api: GamerApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ThreadRepository<GamerPost> {

    override suspend fun getPostThread(threadUrl: String, page: Int, board: Board) = withContext(ioDispatcher) {
        class InconsistentPageEx: Exception("The page has reached the end, because the page of the response is not the same as the request")

        val news = newsDao.readNews(threadUrl) as? News
        if (news != null) {
            val thread = postDao.readPostThread(news.threadUrl, page)
            val isEmpty = thread.size <= 1
            if (!isEmpty) {
                return@withContext thread
            }
        }
        try {
            val req = api.getRequestBuilder()
                .setUrl(threadUrl.toHttpUrl())
                .setPage(page)
                .build()
            val remote = api.getAllPost(req).map { it.toGamerPost(threadUrl) }
            if (remote.first().page != page){
                throw InconsistentPageEx()
            }
            transactionProvider.invoke {
                postDao.upsertAll(remote)
            }
            remote
        } catch (e: InconsistentPageEx) {
            emptyList()
        } catch (e: Exception) {
            Log.e("GamerThreadRepo", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getRePostThread(
        threadUrl: String,
        rePostId: String,
        page: Int,
    ): List<GamerPost> = withContext(ioDispatcher) {
        val head = postDao.readRePost(threadUrl, rePostId)
        val sub = postDao.readRePostThread(threadUrl, rePostId, page)
        listOf(head).plus(sub)
    }

    override suspend fun removePostThread(threadUrl: String) = withContext(ioDispatcher) {
        postDao.clearPostThread(threadUrl)
    }
}