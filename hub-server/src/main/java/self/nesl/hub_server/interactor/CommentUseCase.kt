package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.comment.Comment
import self.nesl.hub_server.data.comment.CommentRepository
import self.nesl.hub_server.data.comment.gamer.GamerComment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentUseCase @Inject constructor(
    private val boardUseCase: BoardUseCase,
    private val gamerThreadRepository: CommentRepository<GamerComment>,
) {
    suspend fun getAllComments(
        commentsUrl: String,
        page: Int
    ): List<Comment> {
        val board = boardUseCase.getBoard(commentsUrl)
        return when (board.host) {
            Host.GAMER -> gamerThreadRepository.getAllComments(commentsUrl, page, board)
            else -> throw NotImplementedError("CommentRepository not implement")
        }
    }
}