package self.nesl.hub_server.data.post.komica

import self.nesl.komica_api.model.KBoard

interface KomicaPostRepository {
    suspend fun getAllNews(board: KBoard, page: Int): List<KomicaPost>
    suspend fun clearAllNews()
}