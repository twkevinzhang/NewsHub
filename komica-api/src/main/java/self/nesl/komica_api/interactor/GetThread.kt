package self.nesl.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.parser._2cat.*
import self.nesl.komica_api.parser.sora.*
import self.nesl.komica_api.toKomicaBoard

class GetThread(
    private val client: OkHttpClient,
) {
    suspend operator fun invoke(url: String): Pair<KPost, List<KPost>> = withContext(Dispatchers.IO) {
        val response = client.newCall(
            Request.Builder()
            .url(url)
            .build()
        ).await()

        when (url.toKomicaBoard()) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2cat ->
                _2catThreadParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("ThreadParser of $url not implemented yet")
        }.parse(Jsoup.parse(response.body?.string()), url)
    }
}