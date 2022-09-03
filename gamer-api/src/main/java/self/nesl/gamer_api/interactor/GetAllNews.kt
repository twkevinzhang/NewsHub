package self.nesl.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost
import self.nesl.gamer_api.parser.BoardParser
import self.nesl.gamer_api.parser.PostParser
import self.nesl.gamer_api.parser.UrlParserImpl
import self.nesl.gamer_api.request.BoardRequestBuilder

class GetAllNews(
    private val client: OkHttpClient,
) {
    suspend operator fun invoke(board: GBoard, page: Int?= null): List<GNews> = withContext(Dispatchers.IO) {
        val req = processPage(board, page)
        val response = client.newCall(req).await()
        BoardParser().parse(Jsoup.parse(response.body()?.string()), board.url)
    }

    private fun processPage(board: GBoard, page: Int?= null): Request {
        return BoardRequestBuilder()
            .url(board.url)
            .setPageReq(page)
            .build()
    }
}