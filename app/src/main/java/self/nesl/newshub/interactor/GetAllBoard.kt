package self.nesl.newshub.interactor

import self.nesl.hub_server.data.post.Board
import self.nesl.hub_server.data.post.Host
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllBoard @Inject constructor(
) {
    suspend fun invoke(id: String): List<Board> {
        return when (id) {
            "Square" -> listOf(
                Board("綜合", "http://2cat.komica.org/~tedc21thc/new", Host.KOMICA)
            )
            else -> throw NotImplementedError()
        }
    }
}