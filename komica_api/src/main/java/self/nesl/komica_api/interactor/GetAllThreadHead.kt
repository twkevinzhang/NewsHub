package self.nesl.komica_api.interactor

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.Board
import self.nesl.komica_api.parser._2cat._2catBoardParser
import self.nesl.komica_api.parser._2cat._2catPostHeadParser
import self.nesl.komica_api.parser._2cat._2catPostParser
import self.nesl.komica_api.parser._2cat._2catUrlParser
import self.nesl.komica_api.parser.sora.SoraBoardParser
import self.nesl.komica_api.parser.sora.SoraPostHeadParser
import self.nesl.komica_api.parser.sora.SoraPostParser
import self.nesl.komica_api.parser.sora.SoraUrlParser
import self.nesl.komica_api.request._2cat._2catBoardRequestBuilder
import self.nesl.komica_api.request.sora.SoraBoardRequestBuilder

class GetAllThreadHead(
    private val client: OkHttpClient,
) {
    suspend operator fun invoke(board: Board, page: Int?= null): List<KPost> {
        val req = processPage(board, page)
        val response = client.newCall(req).await()

        return when (board) {
            is Board.Sora, Board.人外, Board.格鬥遊戲, Board.Idolmaster, Board.`3D-STG`, Board.魔物獵人, Board.`TYPE-MOON` ->
                SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is Board._2catKomica ->
                SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is Board._2cat ->
                _2catBoardParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.toString()), board.url)
    }

    private fun processPage(board: Board, page: Int?= null): Request {
        return when (board) {
            is Board.Sora, Board.人外, Board.格鬥遊戲, Board.Idolmaster, Board.`3D-STG`, Board.魔物獵人, Board.`TYPE-MOON` ->
                SoraBoardRequestBuilder()
            is Board._2catKomica ->
                SoraBoardRequestBuilder()
            is Board._2cat ->
                _2catBoardRequestBuilder()
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }.setPageReq(page).build()
    }
}