package self.nesl.hub_server.data.thread.gamer

import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.thread.Thread

data class GamerThread(
    override val url: String,
    override val post: Post,
    override val rePosts: List<Post>,
): Thread
