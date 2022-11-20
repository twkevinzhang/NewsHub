package self.nesl.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import self.nesl.komica_api.*
import self.nesl.komica_api.request.RequestBuilder

class SoraBoardRequestBuilder: RequestBuilder {
    private lateinit var _httpUrl: HttpUrl

    override fun url(url: String): RequestBuilder {
        this._httpUrl= url.toHttpUrl()
        return this
    }

    override fun setPageReq(page: Int?): RequestBuilder {
        _httpUrl = _httpUrl.newBuilder()
            .apply {
                if (page.isZeroOrNull()) {
                    removeFilename("htm")
                } else {
                    val extra = _httpUrl.pathSegments - _httpUrl.toKBoard().url.toHttpUrl().pathSegments
                    if (extra.isEmpty()) {
                        addFilename("${page}.htm")
                    } else {
                        setFilename("${page}.htm")
                    }
                }
            }
            .build()
        return this
    }

    override fun build(): Request {
        return Request.Builder()
            .url(_httpUrl)
            .build()
    }
}