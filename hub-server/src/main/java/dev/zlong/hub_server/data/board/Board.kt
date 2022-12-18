package dev.zlong.hub_server.data.board

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.zlong.gamer_api.model.GBoard
import dev.zlong.hub_server.data.Host
import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.model.boards

@Entity(tableName = "board")
data class Board(
    @PrimaryKey val url: String,
    val name: String,
    val host: Host,

    /**
     * topic id list
     */
    val subscriber: List<String> = emptyList(),
)

fun Board.toKBoard() =
    boards().first { it.url == url }

fun KBoard.toBoard() =
    Board(name = name, url = url, host = Host.KOMICA)

fun Board.toGBoard() =
    GBoard(name = name, url = url)

fun GBoard.toBoard() =
    Board(name = name, url = url, host = Host.GAMER)