package self.nesl.hub_server.data.board


interface BoardRepository {
    suspend fun getAllBoards(): List<Board>
    suspend fun getBoard(url: String): Board
    suspend fun clearAllNews()
}