package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GPost

class UrlParserImpl: UrlParser {
    override fun parsePostId(url: HttpUrl): String? {
        return url.queryParameter("sn")
    }

    override fun parseBoardId(url: HttpUrl): String? {
        return url.queryParameter("bsn")
    }

    override fun parseThreadId(url: HttpUrl): String? {
        return url.queryParameter("snA")
    }
}