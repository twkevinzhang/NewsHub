package self.nesl.hub_server.data.post.komica

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.toKBoard
import self.nesl.hub_server.data.news.NewsRepository
import self.nesl.hub_server.data.post.PostRepository
import self.nesl.hub_server.data.post.gamer.toGamerPost
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import javax.inject.Inject

class KomicaPostRepositoryImpl @Inject constructor(
    private val api: KomicaApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): PostRepository<KomicaPost> {

    override suspend fun getAll(url: String, page: Int, boardUrl: String): List<KomicaPost> = withContext(ioDispatcher) {
        if (page > 1) return@withContext emptyList()
        val remote = api.getAllPost(url).map { it.toKomicaPost(page, boardUrl) }
        return@withContext remote
    }
}