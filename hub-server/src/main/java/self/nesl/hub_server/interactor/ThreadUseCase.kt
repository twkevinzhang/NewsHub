package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.post.parent
import self.nesl.hub_server.data.thread.Thread
import self.nesl.hub_server.data.thread.komica.KomicaThread
import self.nesl.hub_server.data.thread.komica.KomicaThreadRepository
import self.nesl.komica_api.isKomica
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadUseCase @Inject constructor(
    private val komicaThreadRepository: KomicaThreadRepository,
) {
    suspend fun getThread(
        url: String,
    ): Thread {
        return if (url.isKomica()) {
            komicaThreadRepository.getThread(url)
        } else {
            throw NotImplementedError("ThreadRepository not implement")
        }
    }

    suspend fun getRePostThread(
        url: String,
        rePostId: String,
    ): Thread {
        val thread = getThread(url)
        val head = thread.rePosts.findLast { it.id == rePostId }!!
        val rePosts = thread.rePosts.filter { it.parent().contains(rePostId) }
        return if (url.isKomica()) {
            KomicaThread(thread.url, head, rePosts)
        } else {
            throw NotImplementedError("Thread constructor not implement")
        }
    }
}