package self.nesl.hub_server.data.post

import self.nesl.komica_api.isKomica
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.boards
import self.nesl.komica_api.withFolder

data class Board(val name: String, val url: String, val host: Host)

fun Board.toKBoard() =
    boards().findLast { it.url == url }!!