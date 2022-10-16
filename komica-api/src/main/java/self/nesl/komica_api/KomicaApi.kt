package self.nesl.komica_api

import okhttp3.OkHttpClient
import self.nesl.komica_api.interactor.GetAllBoard
import self.nesl.komica_api.interactor.GetAllNews
import self.nesl.komica_api.interactor.GetThread
import self.nesl.komica_api.model.KBoard

class KomicaApi (
    private val client: OkHttpClient,
) {
    suspend fun getAllBoard() =
        GetAllBoard().invoke()

    suspend fun getAllThreadHead(board: KBoard, page: Int?= null) =
        GetAllNews(client).invoke(board = board, page = page)

    suspend fun getAllThreadHead(url: String) =
        GetAllNews(client).invoke(url.toKomicaBoard())

    suspend fun getThread(url: String) =
        GetThread(client).invoke(url)
}