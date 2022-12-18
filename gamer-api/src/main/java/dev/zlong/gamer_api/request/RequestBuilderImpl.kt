package dev.zlong.gamer_api.request

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

class RequestBuilderImpl: RequestBuilder {
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): RequestBuilder {
        this.builder = url.toHttpUrl().newBuilder()
        return this
    }

    override fun url(url: HttpUrl): RequestBuilder {
        this.builder = url.newBuilder()
        return this
    }

    override fun setBsn(bsn: String?): RequestBuilder {
        return if(bsn == null) removeBsnReq()
        else addBsnReq(bsn)
    }

    private fun addBsnReq(bsn: String): RequestBuilder {
        if (hasBsnReq())
            removeBsnReq()
        builder = builder.addQueryParameter("bsn", bsn)
        return this
    }

    private fun removeBsnReq(): RequestBuilder {
        if(hasBsnReq())
            builder = builder.removeAllQueryParameters("bsn")
        return this
    }

    private fun hasBsnReq(): Boolean {
        return builder.build().queryParameter("bsn").isNullOrBlank().not()
    }

    override fun setSna(sna: String?): RequestBuilder {
        return if(sna == null) removeSnaReq()
        else addSnaReq(sna)
    }

    private fun addSnaReq(bsn: String): RequestBuilder {
        if (hasSnaReq())
            removeBsnReq()
        builder = builder.addQueryParameter("sna", bsn)
        return this
    }

    private fun removeSnaReq(): RequestBuilder {
        if(hasSnaReq())
            builder = builder.removeAllQueryParameters("sna")
        return this
    }

    private fun hasSnaReq(): Boolean {
        return builder.build().queryParameter("sna").isNullOrBlank().not()
    }

    override fun setSnb(snb: String?): RequestBuilder {
        return if(snb == null) removeSnbReq()
        else addSnbReq(snb)
    }

    private fun addSnbReq(bsn: String): RequestBuilder {
        if (hasSnbReq())
            removeSnbReq()
        builder = builder.addQueryParameter("snb", bsn)
        return this
    }

    private fun removeSnbReq(): RequestBuilder {
        if(hasSnbReq())
            builder = builder.removeAllQueryParameters("snb")
        return this
    }

    private fun hasSnbReq(): Boolean {
        return builder.build().queryParameter("snb").isNullOrBlank().not()
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        return if(page == null) removePageReq()
        else addPageReq(page)
    }

    private fun addPageReq(page: Int): RequestBuilder {
        if (hasPageReq())
            removePageReq()
        builder = builder.addQueryParameter("page", page.toString())
        return this
    }

    private fun removePageReq(): RequestBuilder {
        if(hasPageReq())
            builder = builder.removeAllQueryParameters("page")
        return this
    }

    private fun hasPageReq(): Boolean {
        return builder.build().queryParameter("page").isNullOrBlank().not()
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}