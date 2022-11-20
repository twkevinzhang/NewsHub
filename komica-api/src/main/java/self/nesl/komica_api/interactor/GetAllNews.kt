package self.nesl.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
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
import self.nesl.komica_api.toKBoard

class GetAllNews(
    private val client: OkHttpClient,
) {
    suspend fun invoke(req: Request): List<KPost> = withContext(Dispatchers.IO) {
        val board = req.url.toKBoard()
        val response = client.newCall(req).await()
        val urlParser = GetUrlParser().invoke(board)

        when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraBoardParser(SoraPostParser(urlParser, SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraBoardParser(SoraPostParser(urlParser, SoraPostHeadParser()))
            is KBoard._2cat ->
                _2catBoardParser(_2catPostParser(urlParser, _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.body?.string()), board.url)
    }
}