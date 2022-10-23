package self.nesl.hub_server.interactor

import android.util.Log
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.post.PostRepository
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.parent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUseCase @Inject constructor(
    private val boardUseCase: BoardUseCase,
    private val komicaPostRepository: PostRepository<KomicaPost>,
    private val gamerPostRepository: PostRepository<GamerPost>,
) {
    suspend fun getAll(
        url: String,
        page: Int
    ): List<Post> {
        val board = boardUseCase.getBoard(url)
        return when (board.host) {
            Host.KOMICA -> komicaPostRepository.getAll(url, page, board.url)
            Host.GAMER -> gamerPostRepository.getAll(url, page, board.url)
            else -> throw NotImplementedError("ThreadRepository not implement")
        }
    }

    suspend fun getRePostThread(
        url: String,
        rePostId: String,
        page: Int
    ): List<Post> {
        val all = getAll(url, page)
        val top = all.first { it.id == rePostId }
        val rePosts = all.filter { it.parent().contains(rePostId) }
        return listOf(top).plus(rePosts)
    }
}