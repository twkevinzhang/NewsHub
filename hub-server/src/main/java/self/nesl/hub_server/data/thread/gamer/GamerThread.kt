package self.nesl.hub_server.data.thread.gamer

import self.nesl.gamer_api.model.GPost
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.post.gamer.toGamerPost
import self.nesl.hub_server.data.thread.Thread

data class GamerThread(
    override val url: String,
    override val post: Post,
    override val rePosts: List<Post>,
): Thread

fun Pair<GPost, List<GPost>>.toGamerThread(url: String, boardUrl: String) =
    GamerThread(
        url = url,
        post = first.toGamerPost(0, boardUrl),
        rePosts = second.map { it.toGamerPost(0, boardUrl) },
    )