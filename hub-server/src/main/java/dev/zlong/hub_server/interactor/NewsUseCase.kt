package dev.zlong.hub_server.interactor

import dev.zlong.hub_server.data.Host
import dev.zlong.hub_server.data.board.Board
import dev.zlong.hub_server.data.news.NewsRepository
import dev.zlong.hub_server.data.news.gamer.GamerNews
import dev.zlong.hub_server.data.news.komica.KomicaNews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCase @Inject constructor(
    private val komicaNewsRepository: NewsRepository<KomicaNews>,
    private val gamerNewsRepository: NewsRepository<GamerNews>,
) {

    suspend fun getAllNews(boardsWithPage: Map<Board, Int>) =
        boardsWithPage.map {
            when (it.key.host) {
                Host.KOMICA -> komicaNewsRepository.getAllNews(it.key, it.value - 1)
                Host.GAMER -> gamerNewsRepository.getAllNews(it.key, it.value - 1)
                else -> throw NotImplementedError("Host not supported")
            }
        }.flatten()

    suspend fun clearAllNews(boards: Set<Board>) {
        boards.forEach {
            when (it.host) {
                Host.KOMICA -> komicaNewsRepository.clearAllNews()
                Host.GAMER -> gamerNewsRepository.clearAllNews()
                else -> throw NotImplementedError("Host not supported")
            }
        }
    }
}