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
import self.nesl.komica_api.parser.sora.SoraPostHeadParser
import self.nesl.komica_api.parser.sora.SoraPostParser
import self.nesl.komica_api.parser.sora.SoraThreadParser
import self.nesl.komica_api.parser.sora.SoraUrlParser
import self.nesl.komica_api.toKBoard

class GetAllPost(
    private val client: OkHttpClient,
) {
    suspend fun invoke(url: String): List<KPost> = withContext(Dispatchers.IO) {
        val response = client.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).await()
        val urlParser = GetUrlParser().invoke(url.toKBoard())

        when (url.toKBoard()) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraThreadParser(SoraPostParser(urlParser, SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraThreadParser(SoraPostParser(urlParser, SoraPostHeadParser()))
            is KBoard._2cat ->
                _2catThreadParser(_2catPostParser(urlParser, _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("ThreadParser of $url not implemented yet")
        }.parse(Jsoup.parse(response.body?.string()), url)
    }
}