package self.nesl.komica_api.request.sora

import okhttp3.Request
import self.nesl.komica_api.request.RequestBuilder

class SoraBoardRequestBuilder: RequestBuilder {
    private val suffix = "/pixmicat.php?page_num="
    private var url: String = ""

    override fun url(url: String): RequestBuilder {
        this.url= url
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        return if(page == null) removePageReq()
        else addPageReq(page)
    }

    private fun addPageReq(page: Int): RequestBuilder {
        if (hasPageReq())
            removePageReq()
        url += suffix + page
        return this
    }

    private fun removePageReq(): RequestBuilder {
        if(hasPageReq())
            url = url.split(suffix)[0]
        return this
    }

    private fun hasPageReq(): Boolean {
        return url.contains(suffix)
    }

    override fun build(): Request {
        val _url = if (url.last() == '/') url else "$url/"
        return Request.Builder()
            .url(_url)
            .build()
    }
}