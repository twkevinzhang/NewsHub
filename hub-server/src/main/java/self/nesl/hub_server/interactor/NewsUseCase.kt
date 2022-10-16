package self.nesl.hub_server.interactor

import self.nesl.hub_server.data.Board
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.post.komica.KomicaPostRepository
import self.nesl.hub_server.data.toKBoard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCase @Inject constructor(
    private val komicaPostRepository: KomicaPostRepository,
) {

    suspend fun getAllNews(boardsWithPage: Map<Board, Int>) =
        boardsWithPage.map {
            when (it.key.host) {
                Host.KOMICA -> komicaPostRepository.getAllNews(it.key.toKBoard(), it.value - 1)
                else -> throw NotImplementedError("Host not supported")
            }
        }.flatten()

    suspend fun clearAllNews(boards: Set<Board>) {
        boards.forEach {
            when (it.host) {
                Host.KOMICA -> komicaPostRepository.clearAllNews()
                else -> throw NotImplementedError("Host not supported")
            }
        }
    }
}