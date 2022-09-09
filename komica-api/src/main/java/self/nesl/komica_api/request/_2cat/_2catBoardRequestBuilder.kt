package self.nesl.komica_api.request._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import self.nesl.komica_api.request.RequestBuilder

class _2catBoardRequestBuilder: RequestBuilder {
    private lateinit var url: HttpUrl

    override fun url(url: String): RequestBuilder {
        this.url= url.toHttpUrlOrNull()!!
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        return if(page == null) removePageReq()
        else addPageReq(page)
    }

    private fun addPageReq(page: Int): RequestBuilder {
        if (hasPageReq())
            removePageReq()
        url = url.newBuilder()
            .addQueryParameter("page", page.toString())
            .build()
        return this
    }

    private fun removePageReq(): RequestBuilder {
        if(hasPageReq()){
            url = url.newBuilder()
                .removeAllQueryParameters("page")
                .build()
        }
        return this
    }

    private fun hasPageReq(): Boolean {
        return url.queryParameter("page").isNullOrEmpty().not()
    }

    override fun build(): Request {
        return Request.Builder()
            .url(url)
            .build()
    }
}