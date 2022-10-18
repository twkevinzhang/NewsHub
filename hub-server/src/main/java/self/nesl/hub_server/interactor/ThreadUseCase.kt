package self.nesl.hub_server.interactor

import okhttp3.HttpUrl.Companion.toHttpUrl
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.post.parent
import self.nesl.hub_server.data.thread.Thread
import self.nesl.hub_server.data.thread.komica.KomicaThread
import self.nesl.hub_server.data.thread.ThreadRepository
import self.nesl.hub_server.data.thread.gamer.GamerThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadUseCase @Inject constructor(
    private val boardUseCase: BoardUseCase,
    private val komicaThreadRepository: ThreadRepository<KomicaThread>,
    private val gamerThreadRepository: ThreadRepository<GamerThread>,
) {
    suspend fun getThread(
        url: String,
    ): Thread {
        val board = boardUseCase.getBoard(url)
        return when (board.host) {
            Host.KOMICA -> komicaThreadRepository.getThread(url, board.url)
            Host.GAMER -> gamerThreadRepository.getThread(url, board.url)
            else -> throw NotImplementedError("ThreadRepository not implement")
        }
    }

    suspend fun getRePostThread(
        url: String,
        rePostId: String,
    ): Thread {
        val thread = getThread(url)
        val head = thread.rePosts.first { it.id == rePostId }
        val rePosts = thread.rePosts.filter { it.parent().contains(rePostId) }
        return when (boardUseCase.getBoard(url).host) {
            Host.KOMICA -> KomicaThread(thread.url, head, rePosts)
            else -> throw NotImplementedError("Thread constructor not implement")
        }
    }
}