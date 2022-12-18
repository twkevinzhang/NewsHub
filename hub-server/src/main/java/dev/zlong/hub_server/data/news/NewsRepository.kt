package dev.zlong.hub_server.data.news

import dev.zlong.hub_server.data.board.Board

interface NewsRepository<T: News> {
    suspend fun getAllNews(board: Board, page: Int): List<T>
    suspend fun clearAllNews()
}