package self.nesl.gamer_api

import okhttp3.OkHttpClient
import self.nesl.gamer_api.interactor.GetAllBoard
import self.nesl.gamer_api.interactor.GetAllNews
import self.nesl.gamer_api.interactor.GetThread
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews

class GamerApi (
    private val client: OkHttpClient,
) {
    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    suspend fun getAllNews(board: GBoard, page: Int?= null) =
        GetAllNews(client).invoke(board = board, page = page)

    suspend fun getThread(url: String) =
        GetThread(client).invoke(url)
}