package dev.zlong.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.parser._2cat._2catBoardParser
import dev.zlong.komica_api.parser._2cat._2catPostHeadParser
import dev.zlong.komica_api.parser._2cat._2catPostParser
import dev.zlong.komica_api.parser._2cat._2catUrlParser
import dev.zlong.komica_api.parser.sora.*
import dev.zlong.komica_api.request._2cat._2catBoardRequestBuilder
import dev.zlong.komica_api.request.sora.SoraBoardRequestBuilder
import dev.zlong.komica_api.toKBoard

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
                SoraBoardParser(SoraPostParser(urlParser, _2catSoraPostHeadParser(SoraUrlParser())))
            is KBoard._2cat ->
                _2catBoardParser(_2catPostParser(urlParser, _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("BoardParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.body?.string()), board.url)
    }
}