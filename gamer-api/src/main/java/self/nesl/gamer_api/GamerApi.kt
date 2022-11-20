package self.nesl.gamer_api

import okhttp3.OkHttpClient
import okhttp3.Request
import self.nesl.gamer_api.interactor.*
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost

class GamerApi (
    private val client: OkHttpClient,
) {
    fun getRequestBuilder() =
        GetRequestBuilder().invoke()

    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    suspend fun getAllNews(req: Request): List<GNews> {
        return GetAllNews(client).invoke(req)
    }

    suspend fun getAllPost(req: Request): List<GPost> {
        return GetAllPost(client).invoke(req)
    }
}