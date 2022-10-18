package self.nesl.newshub.interactor

import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.interactor.BoardUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardInteractor @Inject constructor(
    private val boardUseCase: BoardUseCase,
) {
    suspend fun getAll(topicId: String): List<Board> {
        return when (topicId) {
            "Square" -> listOf(
                boardUseCase.getBoard("https://gaia.komica.org/00"),
                boardUseCase.getBoard("https://2cat.komica.org/~tedc21thc/new"),
                boardUseCase.getBoard("https://forum.gamer.com.tw/B.php?bsn=60076"),
            )
            else -> throw NotImplementedError()
        }
    }

    suspend fun get(url: String): Board {
        return boardUseCase.getBoard(url)
    }
}