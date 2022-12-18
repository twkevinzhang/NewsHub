package dev.zlong.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.model.boards
import dev.zlong.komica_api.parser.UrlParser
import dev.zlong.komica_api.parser._2cat._2catUrlParser
import dev.zlong.komica_api.parser.sora.SoraUrlParser
import dev.zlong.komica_api.toKBoard

class GetUrlParser {
    fun invoke(board: KBoard): UrlParser {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraUrlParser()
            is KBoard._2catKomica ->
                SoraUrlParser()
            is KBoard._2cat ->
                _2catUrlParser()
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }
    }
}