package dev.zlong.komica_api.request

import dev.zlong.komica_api.model.KBoard
import okhttp3.HttpUrl
import okhttp3.Request

interface ThreadRequestBuilder: RequestBuilder {
    fun setUrl(url: HttpUrl): ThreadRequestBuilder
}