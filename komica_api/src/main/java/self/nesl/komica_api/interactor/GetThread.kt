package self.nesl.komica_api.interactor

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.parser._2cat.*
import self.nesl.komica_api.parser.sora.*

class GetThread(
    private val client: OkHttpClient,
) {
    suspend operator fun invoke(board: KBoard): Pair<KPost, List<KPost>> {
        val response = client.newCall(
            Request.Builder()
            .url(board.url)
            .build()
        ).await()

        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2catKomica ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is KBoard._2cat ->
                _2catThreadParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("ThreadParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.toString()), board.url)
    }
}