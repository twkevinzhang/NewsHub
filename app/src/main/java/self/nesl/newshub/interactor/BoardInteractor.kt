package self.nesl.newshub.interactor

import self.nesl.hub_server.data.Board
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.toBoard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardInteractor @Inject constructor(
) {
    suspend fun getAll(topicId: String): List<Board> {
        return when (topicId) {
            "Square" -> listOf(
                "https://gaia.komica.org/00".toBoard(),
                "https://2cat.komica.org/~tedc21thc/new".toBoard(),
            )
            else -> throw NotImplementedError()
        }
    }
}