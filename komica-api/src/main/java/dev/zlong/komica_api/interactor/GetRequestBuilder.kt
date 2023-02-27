package dev.zlong.komica_api.interactor

import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.request.BoardRequestBuilder
import dev.zlong.komica_api.request.RequestBuilder
import dev.zlong.komica_api.request.ThreadRequestBuilder
import dev.zlong.komica_api.request._2cat._2catRequestBuilder
import dev.zlong.komica_api.request.sora.SoraBoardRequestBuilder
import dev.zlong.komica_api.request.sora.SoraThreadRequestBuilder

class GetRequestBuilder {
    fun forBoard(board: KBoard): BoardRequestBuilder {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraBoardRequestBuilder().setBoard(board)
            is KBoard._2catKomica ->
                SoraBoardRequestBuilder().setBoard(board)
            is KBoard._2cat ->
                _2catRequestBuilder().setBoard(board)
            else ->
                throw NotImplementedError("BoardRequestBuilder of $board not implemented yet")
        }
    }

    fun forThread(board: KBoard): ThreadRequestBuilder {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraThreadRequestBuilder().setBoard(board)
            is KBoard._2catKomica ->
                SoraThreadRequestBuilder().setBoard(board)
            is KBoard._2cat ->
                _2catRequestBuilder().setBoard(board)
            else ->
                throw NotImplementedError("ThreadRequestBuilder of $board not implemented yet")
        }
    }
}