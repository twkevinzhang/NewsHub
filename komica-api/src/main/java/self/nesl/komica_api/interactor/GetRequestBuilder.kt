package self.nesl.komica_api.interactor

import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.request.RequestBuilder
import self.nesl.komica_api.request._2cat._2catBoardRequestBuilder
import self.nesl.komica_api.request.sora.SoraBoardRequestBuilder

class GetRequestBuilder {
    fun invoke(board: KBoard): RequestBuilder {
        return when (board) {
            is KBoard.Sora, KBoard.人外, KBoard.格鬥遊戲, KBoard.Idolmaster, KBoard.`3D-STG`, KBoard.魔物獵人, KBoard.`TYPE-MOON` ->
                SoraBoardRequestBuilder()
            is KBoard._2catKomica ->
                SoraBoardRequestBuilder()
            is KBoard._2cat ->
                _2catBoardRequestBuilder()
            else ->
                throw NotImplementedError("BoardRequestBuilder of $board not implemented yet")
        }
    }
}