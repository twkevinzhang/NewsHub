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
import self.nesl.gamer_api.parser.PostParser
import self.nesl.gamer_api.parser.ThreadParser
import self.nesl.gamer_api.parser.UrlParserImpl

class GetThread(
    private val client: OkHttpClient,
) {
    suspend fun invoke(news: GNews): Pair<GPost, List<GPost>> = withContext(Dispatchers.IO) {
        val response = client.newCall(
            Request.Builder()
            .url(news.url)
            .build()
        ).await()

        ThreadParser(PostParser(UrlParserImpl())).parse(Jsoup.parse(response.body()?.string()), news.url)
    }
}