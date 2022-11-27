package self.nesl.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.*
import self.nesl.komica_api.parser._2cat._2catPostHeadParser
import self.nesl.komica_api.parser._2cat._2catPostParser
import self.nesl.komica_api.parser._2cat._2catThreadParser
import self.nesl.komica_api.parser._2cat._2catUrlParser
import self.nesl.komica_api.parser.sora.*
import self.nesl.komica_api.toKBoard

class GetAllPost(
    private val client: OkHttpClient,
) {
    suspend fun invoke(req: Request): List<KPost> = withContext(Dispatchers.IO) {
        val response = client.newCall(req).await()
        val board = req.url.toKBoard()
        val urlParser = GetUrlParser().invoke(board)

        when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraThreadParser(SoraPostParser(urlParser, SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraThreadParser(SoraPostParser(urlParser, _2catSoraPostHeadParser(SoraUrlParser())))
            is KBoard._2cat ->
                _2catThreadParser(_2catPostParser(urlParser, _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("ThreadParser of ${req.url} not implemented yet")
        }.parse(Jsoup.parse(response.body?.string()), req.url.toString())
    }

    suspend fun withFillReplyTo(req: Request): List<KPost> = withContext(Dispatchers.IO) {
        val urlParser = GetUrlParser().invoke(req.url.toKBoard())
        val headPostId = urlParser.parseHeadPostId(req.url)!!
        val origin = invoke(req)
        origin.map { p ->
            if (p.replyTo().isEmpty()) {
                val originContent = p.content
                p.copy(content = listOf(KReplyTo(headPostId)).plus(originContent))
            } else {
                p
            }
        }
    }
}