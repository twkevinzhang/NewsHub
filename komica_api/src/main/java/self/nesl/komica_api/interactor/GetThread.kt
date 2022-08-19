package self.nesl.komica_api.interactor

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.Board
import self.nesl.komica_api.parser._2cat.*
import self.nesl.komica_api.parser.sora.*

class GetThread(
    private val client: OkHttpClient,
) {
    suspend operator fun invoke(board: Board): Pair<KPost, List<KPost>> {
        val response = client.newCall(
            Request.Builder()
            .url(board.url)
            .build()
        ).await()

        return when (board) {
            is Board.Sora, Board.人外, Board.格鬥遊戲, Board.Idolmaster, Board.`3D-STG`, Board.魔物獵人, Board.`TYPE-MOON` ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is Board._2catKomica ->
                SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
            is Board._2cat ->
                _2catThreadParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
            else ->
                throw NotImplementedError("ThreadParser of $board not implemented yet")
        }.parse(Jsoup.parse(response.toString()), board.url)
    }
}