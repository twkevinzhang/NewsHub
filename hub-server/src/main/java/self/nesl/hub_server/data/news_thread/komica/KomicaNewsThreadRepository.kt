package self.nesl.hub_server.data.news_thread.komica

import self.nesl.hub_server.data.news_head.Topic

interface KomicaNewsThreadRepository {
    suspend fun getNewsThread(url: String): KomicaNewsThread
}