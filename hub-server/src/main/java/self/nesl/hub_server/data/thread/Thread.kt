package self.nesl.hub_server.data.thread

import self.nesl.hub_server.data.post.Post


interface Thread {
    val url: String
    val post: Post
    val rePosts: List<Post>
}