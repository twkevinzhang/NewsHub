package dev.zlong.komica_api.interactor

import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.request.RequestBuilder
import dev.zlong.komica_api.request._2cat._2catRequestBuilder
import dev.zlong.komica_api.request.sora.SoraRequestBuilder

class GetRequestBuilder {
    fun invoke(board: KBoard): RequestBuilder {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraRequestBuilder().setBoard(board)
            is KBoard._2catKomica ->
                SoraRequestBuilder().setBoard(board)
            is KBoard._2cat ->
                _2catRequestBuilder().setBoard(board)
            else ->
                throw NotImplementedError("BoardRequestBuilder of $board not implemented yet")
        }
    }
}