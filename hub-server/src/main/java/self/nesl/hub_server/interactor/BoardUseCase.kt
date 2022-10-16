package self.nesl.hub_server.interactor

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

    suspend fun getBoard(url: String) =
        boardRepository.getBoard(url)
}