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

    override fun parsePage(url: HttpUrl): Int {
        return url.queryParameter("page")?.toInt() ?: 1
    }

    override fun parseThreadUrl(url: HttpUrl): String {
        if (
            parseBoardId(url) == null ||
            parseThreadId(url) == null
        ) throw IllegalArgumentException()

        val builder = url.newBuilder()
        builder.removeAllQueryParameters("sn")
        builder.removeAllQueryParameters("page")
        return builder.build().toUrl().toString()
    }
}