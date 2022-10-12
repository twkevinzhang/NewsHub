package self.nesl.hub_server.data.thread.komica

import self.nesl.hub_server.data.thread.Post
import self.nesl.hub_server.data.thread.Thread

data class KomicaThread (
    override val url: String,
    override val post: Post,
    override val rePosts: List<Post>,
): Thread