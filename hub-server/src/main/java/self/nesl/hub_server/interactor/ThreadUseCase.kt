package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.thread.Thread
import self.nesl.hub_server.data.thread.komica.KomicaThread
import self.nesl.hub_server.data.thread.komica.KomicaThreadRepository
import self.nesl.hub_server.data.thread.parent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadUseCase @Inject constructor(
    private val komicaThreadRepository: KomicaThreadRepository,
) {
    suspend fun getThread(
        url: String,
    ): Thread {
        return komicaThreadRepository.getThread(url)
    }

    suspend fun getRePostThread(
        url: String,
        rePostId: String,
    ): Thread {
        val thread = komicaThreadRepository.getThread(url)
        val head = thread.rePosts.findLast { it.id == rePostId }!!
        val rePosts = thread.rePosts.filter { it.parent().contains(rePostId) }
        return KomicaThread(thread.url, head, rePosts)
    }
}