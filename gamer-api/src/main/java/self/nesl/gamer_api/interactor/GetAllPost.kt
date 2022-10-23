package self.nesl.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost
import self.nesl.gamer_api.parser.PostParser
import self.nesl.gamer_api.parser.ThreadParser
import self.nesl.gamer_api.parser.UrlParserImpl
import self.nesl.gamer_api.request.RequestBuilderImpl

class GetAllPost(
    private val client: OkHttpClient,
) {
    suspend fun invoke(threadUrl: String, page: Int): List<GPost> = withContext(Dispatchers.IO) {
        val req = RequestBuilderImpl()
            .url(threadUrl)
            .setPageReq(page)
            .build()
        val response = client.newCall(req).await()
        parse(response, req.url.toString())
    }

    suspend fun invoke(url: String): List<GPost> = withContext(Dispatchers.IO) {
        val req = RequestBuilderImpl()
            .url(url)
            .build()
        val response = client.newCall(req).await()

        parse(response, url)
    }

    private fun parse(response: Response, url: String) =
        ThreadParser(PostParser(UrlParserImpl()), UrlParserImpl()).parse(Jsoup.parse(response.body?.string()), url)
}