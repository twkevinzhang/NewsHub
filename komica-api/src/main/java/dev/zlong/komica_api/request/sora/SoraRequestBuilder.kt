package dev.zlong.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import dev.zlong.komica_api.*
import dev.zlong.komica_api.model.KBoard
import dev.zlong.komica_api.request.RequestBuilder

class SoraRequestBuilder: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun setUrl(url: HttpUrl): RequestBuilder {
        this.builder = if (!url.isFile("pixmicat", "php")) {
            url.newBuilder().addFilename("pixmicat", "php")
        } else {
            url.newBuilder()
        }
        return this
    }

    override fun setBoard(board: KBoard): RequestBuilder {
        setUrl(board.url.toHttpUrl())
        return this
    }

    override fun setRes(res: String?): RequestBuilder {
        return if(res == null) removeQuery("res")
        else addQuery("res", res)
    }

    private fun addQuery(queryName: String, value: String): RequestBuilder {
        if (hasQuery(queryName))
            removeQuery(queryName)
        builder = builder.addQueryParameter(queryName, value)
        return this
    }

    private fun hasQuery(queryName: String): Boolean {
        return builder.build().queryParameter(queryName).isNullOrBlank().not()
    }

    private fun removeQuery(queryName: String): RequestBuilder {
        if(hasQuery(queryName))
            builder = builder.removeAllQueryParameters(queryName)
        return this
    }

    override fun setFragment(reply: String?): RequestBuilder {
        return if(reply == null) removeFragment()
        else addFragment(reply)
    }

    private fun addFragment(value: String): RequestBuilder {
        if (hasFragment())
            removeFragment()
        builder = builder.fragment(value)
        return this
    }

    private fun hasFragment(): Boolean {
        return builder.build().fragment.isNullOrBlank().not()
    }

    private fun removeFragment(): RequestBuilder {
        if(hasFragment())
            builder = builder.fragment(null)
        return this
    }

    override fun setPage(page: Int?): SoraRequestBuilder {
        builder = builder
            .apply {
                if (page.isZeroOrNull()) {
                    removeFilename("htm")
                } else {
                    val _httpUrl = builder.build()
                    val extra = _httpUrl.pathSegments - _httpUrl.toKBoard().url.toHttpUrl().pathSegments
                    if (extra.isEmpty()) {
                        addFilename("$page", "htm")
                    } else {
                        setFilename("${page}.htm")
                    }
                }
            }
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}