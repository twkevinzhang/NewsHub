package self.nesl.hub_server.data.thread

interface ThreadRepository<T: Thread> {
    suspend fun getThread(url: String): T
}