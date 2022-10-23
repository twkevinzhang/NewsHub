package self.nesl.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.parser.BoardParser
import self.nesl.gamer_api.parser.NewsParser
import self.nesl.gamer_api.request.RequestBuilderImpl

class GetAllNews(
    private val client: OkHttpClient,
) {
    /**
     * 取得指定 Board 底下的 News，這些 News 與 Thread 不同，News 只是 Thread 的簡單資訊，而 Thread 內包含了整個討論串的原 PO 及回文 (RePost)
     */
    suspend fun invoke(board: GBoard, page: Int?= null): List<GNews> = withContext(Dispatchers.IO) {
        val req = processPage(board, page)
        val response = client.newCall(req).await()
        BoardParser(NewsParser()).parse(Jsoup.parse(response.body?.string()), board.url)
    }

    private fun processPage(board: GBoard, page: Int?= null): Request {
        return RequestBuilderImpl()
            .url(board.url)
            .setPageReq(page)
            .build()
    }
}