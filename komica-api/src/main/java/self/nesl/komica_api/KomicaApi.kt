package self.nesl.komica_api

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import self.nesl.komica_api.interactor.GetAllBoard
import self.nesl.komica_api.interactor.GetAllNews
import self.nesl.komica_api.interactor.GetAllPost
import self.nesl.komica_api.interactor.GetUrlParser
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.KPost

class KomicaApi (
    private val client: OkHttpClient,
) {
    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    /**
     * 通常用於取得看板上的所有貼文
     */
    @Deprecated("請改用 getAllPost(url: String)")
    suspend fun getAllPost(board: KBoard, page: Int?= null) =
        GetAllNews(client).invoke(board = board, page = page)

    /**
     * 通常用於取得貼文底下的所有回覆貼文
     */
    suspend fun getAllPost(url: String): List<KPost> {
        val urlParser = GetUrlParser().invoke(url.toKBoard())
        val httpUrl = url.toHttpUrl()
        return if (urlParser.hasPostId(httpUrl)) {
            GetAllPost(client).invoke(url)
        } else if (urlParser.hasBoardId(httpUrl)) {
            GetAllNews(client).invoke(url)
        } else {
            throw IllegalArgumentException("is illegal url $url")
        }
    }
}