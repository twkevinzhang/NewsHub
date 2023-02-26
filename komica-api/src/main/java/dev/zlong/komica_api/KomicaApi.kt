package dev.zlong.komica_api

import okhttp3.OkHttpClient
import okhttp3.Request
import dev.zlong.komica_api.interactor.*
import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.request.BoardRequestBuilder
import dev.zlong.komica_api.request.RequestBuilder
import dev.zlong.komica_api.request.ThreadRequestBuilder

class KomicaApi (
    private val client: OkHttpClient,
) {
    fun getBoardRequestBuilder(board: KBoard): BoardRequestBuilder {
        return GetRequestBuilder().forBoard(board)
    }

    fun getThreadRequestBuilder(board: KBoard): ThreadRequestBuilder {
        return GetRequestBuilder().forThread(board)
    }

    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    /**
     * 通常用於取得貼文底下的所有回覆貼文
     */
    suspend fun getAllPost(req: Request): List<KPost> {
        val urlParser = GetUrlParser().invoke(req.url.toKBoard())
        return if (urlParser.hasPostId(req.url)) {
            GetAllPost(client).invoke(req)
        } else {
            GetAllNews(client).invoke(req)
        }
    }
}