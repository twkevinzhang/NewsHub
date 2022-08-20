package self.nesl.komica_api.interactor

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KBoard
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
    suspend operator fun invoke(board: KBoard, page: Int?= null): List<KPost> {
        val req = processPage(board, page)
        val response = client.newCall(req).await()

        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2cat ->
                _2catBoardParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.body()?.string()), board.url)
    }

    private fun processPage(board: KBoard, page: Int?= null): Request {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraBoardRequestBuilder()
            is KBoard._2catKomica ->
                SoraBoardRequestBuilder()
            is KBoard._2cat ->
                _2catBoardRequestBuilder()
            else ->
                throw NotImplementedError("BoardRequestBuilder of $board not implemented yet")
        }
            .url(board.url)
            .setPageReq(page)
            .build()
    }
}