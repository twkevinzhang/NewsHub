package dev.zlong.komica_api.request

import dev.zlong.komica_api.model.KBoard
import okhttp3.HttpUrl
import okhttp3.Request

interface BoardRequestBuilder: RequestBuilder {
    fun setUrl(url: HttpUrl): BoardRequestBuilder
    fun setPage(page: Int?): BoardRequestBuilder
}