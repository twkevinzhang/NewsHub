package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import self.nesl.komica_api.parser.UrlParser

class SoraUrlParser: UrlParser {
    override fun parsePostId(url: HttpUrl): String? {
        val postId = url.queryParameter("res")
        val fragment = url.fragment // "#12345678"
        return fragment?.substring(1) ?: postId
    }

    override fun parseBoardId(url: HttpUrl): String? {
        TODO("Not yet implemented")
    }
}