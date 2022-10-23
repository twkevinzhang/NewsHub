package self.nesl.hub_server.data.post

interface PostRepository<T: Post> {
    suspend fun getAll(url: String, page: Int, boardUrl: String): List<T>
}