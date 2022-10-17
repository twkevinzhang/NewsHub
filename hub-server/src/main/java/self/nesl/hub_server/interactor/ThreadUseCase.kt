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
        val board = url.toBoard()
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
        return when (url.toBoard().host) {
            Host.KOMICA -> KomicaThread(thread.url, head, rePosts)
            else -> throw NotImplementedError("Thread constructor not implement")
        }
    }

    private suspend fun String.toBoard(): Board {
        val boards = boardUseCase.getAllBoards()
        for (board in boards) {
            when (board.host) {
                Host.KOMICA -> {
                    if (this.startsWith(board.url)) return board
                }
                Host.GAMER -> {
                    if (board.url.toHttpUrl().host == this.toHttpUrl().host) return board
                }
                else -> continue
            }
        }
        throw NotImplementedError("Board host not implement")
    }
}