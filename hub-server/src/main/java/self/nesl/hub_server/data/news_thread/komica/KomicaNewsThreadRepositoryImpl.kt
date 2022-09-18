package self.nesl.hub_server.data.news_thread.komica

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.news_head.*
import self.nesl.hub_server.toKomicaNewsThread
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class KomicaNewsThreadRepositoryImpl @Inject constructor(
    private val api: KomicaApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaNewsThreadRepository {

    override  suspend fun getNewsThread(url: String): KomicaNewsThread = withContext(ioDispatcher) {
        val remote = api.getThread(url).toKomicaNewsThread(url)
        return@withContext remote
    }
}