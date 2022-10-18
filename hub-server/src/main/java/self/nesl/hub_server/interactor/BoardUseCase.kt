package self.nesl.hub_server.interactor

import okhttp3.HttpUrl.Companion.toHttpUrl
import self.nesl.hub_server.data.*
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.BoardRepository
import self.nesl.hub_server.data.board.toBoard
import self.nesl.komica_api.model.boards
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
) {

    suspend fun getAllBoards() =
        boardRepository.getAllBoards()

    suspend fun getBoard(url: String): Board {
        val boards = getAllBoards()
        for (board in boards) {
            when (board.host) {
                Host.KOMICA -> {
                    if (url.startsWith(board.url)) return board
                }
                Host.GAMER -> {
                    if (board.url.toHttpUrl().host == url.toHttpUrl().host) return board
                }
                else -> continue
            }
        }
        throw NotImplementedError("url $url can't find board")
    }
}