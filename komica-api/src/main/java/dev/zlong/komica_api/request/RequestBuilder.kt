package dev.zlong.komica_api.request

import dev.zlong.komica_api.model.KBoard
import okhttp3.HttpUrl
import okhttp3.Request

interface RequestBuilder {
    fun setUrl(url: HttpUrl): RequestBuilder
    fun setBoard(board: KBoard): RequestBuilder
    fun setRes(res: String?): RequestBuilder
    fun setFragment(reply: String?): RequestBuilder
    fun setPage(page: Int?): RequestBuilder
    fun build(): Request
}