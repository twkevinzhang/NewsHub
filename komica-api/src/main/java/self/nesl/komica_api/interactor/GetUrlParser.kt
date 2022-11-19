package self.nesl.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.boards
import self.nesl.komica_api.parser.UrlParser
import self.nesl.komica_api.parser._2cat._2catUrlParser
import self.nesl.komica_api.parser.sora.SoraUrlParser
import self.nesl.komica_api.toKBoard

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