package dev.zlong.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.request.RequestBuilder

class _2catBoardRequestBuilder: RequestBuilder {
    private lateinit var _httpUrl: HttpUrl

    override fun url(url: String): RequestBuilder {
        this._httpUrl = url.toHttpUrl()
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        _httpUrl = _httpUrl.newBuilder()
            .setQueryParameter("page", page.toString())
            .build()
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(_httpUrl)
            .build()
    }
}