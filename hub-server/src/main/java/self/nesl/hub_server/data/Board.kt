package self.nesl.hub_server.data

import self.nesl.komica_api.model.boards

data class Board(val name: String, val url: String, val host: Host)

fun Board.toKBoard() =
    boards().findLast { it.url == url }!!