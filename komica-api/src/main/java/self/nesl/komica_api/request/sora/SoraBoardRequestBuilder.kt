package self.nesl.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import self.nesl.komica_api.request.RequestBuilder

class SoraBoardRequestBuilder: RequestBuilder {
    private lateinit var _httpUrl: HttpUrl

    override fun url(url: String): RequestBuilder {
        this._httpUrl= url.toHttpUrl()
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        return if(page == null) removePageReq()
        else addPageReq(page)
    }

    private fun addPageReq(page: Int): RequestBuilder {
        if (hasPageReq())
            removePageReq()
        _httpUrl = _httpUrl.newBuilder()
            .addQueryParameter("page_num", page.toString())
            .build()
        return this
    }

    private fun removePageReq(): RequestBuilder {
        if(hasPageReq())
            _httpUrl = _httpUrl.newBuilder()
                .removeAllQueryParameters("page_num")
                .build()
        return this
    }

    private fun hasPageReq(): Boolean {
        return _httpUrl.queryParameter("page_num").isNullOrBlank().not()
    }

    override fun build(): Request {
        return Request.Builder()
            .url(_httpUrl)
            .build()
    }
}