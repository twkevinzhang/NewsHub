package dev.zlong.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import ru.gildor.coroutines.okhttp.await
import dev.zlong.gamer_api.model.GBoard
import dev.zlong.gamer_api.model.GNews
import dev.zlong.gamer_api.model.GPost
import dev.zlong.gamer_api.parser.PostParser
import dev.zlong.gamer_api.parser.ThreadParser
import dev.zlong.gamer_api.parser.UrlParserImpl
import dev.zlong.gamer_api.request.RequestBuilderImpl

class GetAllPost(
    private val client: OkHttpClient,
) {
    suspend fun invoke(req: Request): List<GPost> = withContext(Dispatchers.IO) {
        val response = client.newCall(req).await()
        parse(response, req)
    }

    private fun parse(res: Response, req: Request) =
        ThreadParser(PostParser(UrlParserImpl()), UrlParserImpl(), RequestBuilderImpl()).parse(res.body!!, req)
}