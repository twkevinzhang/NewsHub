package self.nesl.hub_server.data

import self.nesl.komica_api.model.boards

data class Board(val name: String, val url: String, val host: Host)

fun Board.toKBoard() =
    boards().first { it.url == url }

fun String.toBoard(): Board {
    val kBoard = boards().findLast { this.startsWith(it.url) }
    if (kBoard != null) {
        return Board(kBoard.name, kBoard.url, Host.KOMICA)
    }
    throw Exception("$this can't to Board")
}