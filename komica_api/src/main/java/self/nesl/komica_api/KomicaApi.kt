package self.nesl.komica_api

import okhttp3.OkHttpClient
import self.nesl.komica_api.interactor.GetAllBoard
import self.nesl.komica_api.interactor.GetAllThreadHead
import self.nesl.komica_api.interactor.GetThread
import self.nesl.komica_api.model.KBoard

class KomicaApi (
    private val client: OkHttpClient,
) {
    fun getAllBoard() =
        GetAllBoard().invoke()

    suspend fun getAllThreadHead(board: KBoard, page: Int?= null) =
        GetAllThreadHead(client).invoke(board = board, page = page)

    suspend fun getAllThreadHead(url: String) =
        GetAllThreadHead(client).invoke(url.toKomicaBoard())

    suspend fun getThread(board: KBoard) =
        GetThread(client).invoke(board)

    suspend fun getThread(url: String) =
        GetThread(client).invoke(url.toKomicaBoard())
}