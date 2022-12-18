package dev.zlong.hub_server.data.comment

import dev.zlong.hub_server.data.board.Board


interface CommentRepository<T: Comment> {
    suspend fun getAllComments(commentsUrl: String, page: Int, board: Board): List<T>
}