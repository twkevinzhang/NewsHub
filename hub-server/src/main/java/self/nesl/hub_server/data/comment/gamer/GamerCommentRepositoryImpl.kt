package self.nesl.hub_server.data.comment.gamer

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.comment.CommentRepository
import self.nesl.newshub.di.IoDispatcher
import javax.inject.Inject

class GamerCommentRepositoryImpl @Inject constructor(
    private val api: GamerApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): CommentRepository<GamerComment> {

    override suspend fun getAllComments(
        commentsUrl: String,
        page: Int,
        board: Board
    ) = withContext(ioDispatcher) {
        try {
            val req = api.getRequestBuilder()
                .url(commentsUrl)
                .build()
            val remote = api.getAllComment(req).map { it.toGamerComment(page) }
            remote
        } catch (e: Exception) {
            Log.e("GamerCommentRepo", e.stackTraceToString())
            emptyList()
        }
    }
}