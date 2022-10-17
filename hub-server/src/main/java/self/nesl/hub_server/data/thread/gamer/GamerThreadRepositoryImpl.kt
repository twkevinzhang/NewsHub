package self.nesl.hub_server.data.thread.gamer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.hub_server.data.thread.ThreadRepository
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class GamerThreadRepositoryImpl @Inject constructor(
    private val api: GamerApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ThreadRepository<GamerThread> {

    override suspend fun getThread(url: String, boardUrl: String): GamerThread = withContext(ioDispatcher) {
        val remote = api.getThread(url).toGamerThread(url, boardUrl)
        return@withContext remote
    }
}