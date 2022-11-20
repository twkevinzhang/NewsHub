package self.nesl.komica_api

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import self.nesl.komica_api.interactor.*
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.request.RequestBuilder

class KomicaApi (
    private val client: OkHttpClient,
) {
    fun getRequestBuilder(board: KBoard): RequestBuilder {
        return GetRequestBuilder().invoke(board)
    }

    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    /**
     * 通常用於取得貼文底下的所有回覆貼文
     */
    suspend fun getAllPost(req: Request): List<KPost> {
        val urlParser = GetUrlParser().invoke(req.url.toKBoard())
        return if (urlParser.hasPostId(req.url)) {
            GetAllPost(client).withFillReplyTo(req)
        } else {
            GetAllNews(client).invoke(req)
        }
    }
}