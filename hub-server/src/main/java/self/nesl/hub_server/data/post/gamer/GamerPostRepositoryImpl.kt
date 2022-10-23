package self.nesl.hub_server.data.post.gamer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.hub_server.data.post.PostRepository
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class GamerPostRepositoryImpl @Inject constructor(
    private val api: GamerApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): PostRepository<GamerPost> {

    override suspend fun getAll(url: String, page: Int, boardUrl: String) = withContext(ioDispatcher) {
        val remote = api.getAllPost(url, page).map { it.toGamerPost(page, boardUrl) }
        return@withContext remote
    }
}