package self.nesl.hub_server.data.news.gamer

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.toGBoard
import self.nesl.hub_server.data.news.NewsRepository
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import javax.inject.Inject

class GamerNewsRepositoryImpl @Inject constructor(
    private val dao: GamerNewsDao,
    private val api: GamerApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): NewsRepository<GamerNews> {

    override suspend fun getAllNews(board: Board, page: Int): List<GamerNews> = withContext(ioDispatcher) {
        val news = dao.readAll(board.url, page)
        if (news.isNotEmpty()) {
            news
        } else {
            try {
                val remote = api.getAllNews(board.toGBoard(), page.takeIf { it != 0 }).map { it.toGamerPost(page, board.url) }
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("GamerPostRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    override suspend fun clearAllNews() = withContext(ioDispatcher) {
        dao.clearAll()
    }
}