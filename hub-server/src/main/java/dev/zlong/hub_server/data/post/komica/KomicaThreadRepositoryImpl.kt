package dev.zlong.hub_server.data.post.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import dev.zlong.hub_server.data.board.Board
import dev.zlong.hub_server.data.board.toKBoard
import dev.zlong.hub_server.data.news.News
import dev.zlong.hub_server.data.news.komica.KomicaNewsDao
import dev.zlong.hub_server.data.post.ThreadRepository
import dev.zlong.hub_server.di.TransactionProvider
import dev.zlong.komica_api.KomicaApi
import dev.zlong.newshub.di.IoDispatcher
import javax.inject.Inject

class KomicaThreadRepositoryImpl @Inject constructor(
    private val newsDao: KomicaNewsDao,
    private val postDao: KomicaPostDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ThreadRepository<KomicaPost> {

    override suspend fun getPostThread(threadUrl: String, page: Int, board: Board): List<KomicaPost> = withContext(ioDispatcher) {
        if (page > 1) return@withContext emptyList()
        val news = newsDao.readNews(threadUrl) as? News
        if (news != null) {
            val thread = postDao.readPostThread(news.threadUrl)
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
                postDao.upsertAll(remote)
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
        val head = postDao.readRePost(threadUrl, rePostId)
        val sub = postDao.readRePostThread(threadUrl, rePostId, page)
        listOf(head).plus(sub)
    }

    override suspend fun removePostThread(threadUrl: String) = withContext(ioDispatcher) {
        postDao.clearPostThread(threadUrl)
    }
}