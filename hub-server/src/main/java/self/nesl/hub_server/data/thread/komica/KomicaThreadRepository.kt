package self.nesl.hub_server.data.thread.komica

interface KomicaThreadRepository {
    suspend fun getThread(url: String): KomicaThread
}