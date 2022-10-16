package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.toGBoard
import self.nesl.hub_server.data.board.toKBoard
import self.nesl.hub_server.data.news.gamer.GamerNewsRepository
import self.nesl.hub_server.data.post.komica.KomicaPostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCase @Inject constructor(
    private val komicaPostRepository: KomicaPostRepository,
    private val gamerNewsRepository: GamerNewsRepository,
) {

    suspend fun getAllNews(boardsWithPage: Map<Board, Int>) =
        boardsWithPage.map {
            when (it.key.host) {
                Host.KOMICA -> komicaPostRepository.getAllNews(it.key.toKBoard(), it.value - 1)
                Host.GAMER -> gamerNewsRepository.getAllNews(it.key.toGBoard(), it.value - 1)
                else -> throw NotImplementedError("Host not supported")
            }
        }.flatten()

    suspend fun clearAllNews(boards: Set<Board>) {
        boards.forEach {
            when (it.host) {
                Host.KOMICA -> komicaPostRepository.clearAllNews()
                Host.GAMER -> gamerNewsRepository.clearAllNews()
                else -> throw NotImplementedError("Host not supported")
            }
        }
    }
}