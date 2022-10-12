package self.nesl.hub_server.data.thread


interface Thread {
    val url: String
    val post: Post
    val rePosts: List<Post>
}