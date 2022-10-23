package self.nesl.gamer_api.request

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

class RequestBuilderImpl: RequestBuilder {
    private lateinit var _httpUrl: HttpUrl
    private lateinit var builder: HttpUrl.Builder

    override fun url(url: String): RequestBuilder {
        this._httpUrl= url.toHttpUrl()
        this.builder = this._httpUrl.newBuilder()
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        return if(page == null) removePageReq()
        else addPageReq(page)
    }

    private fun addPageReq(page: Int): RequestBuilder {
        if (hasPageReq())
            removePageReq()
        builder.addQueryParameter("page", page.toString())
        return this
    }

    private fun removePageReq(): RequestBuilder {
        if(hasPageReq())
            builder.removeAllQueryParameters("page")
        return this
    }

    private fun hasPageReq(): Boolean {
        return _httpUrl.queryParameter("page").isNullOrBlank().not()
    }

    override fun build(): Request {
        return Request.Builder()
            .url(builder.build())
            .build()
    }
}