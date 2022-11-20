package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GPost

class UrlParserImpl: UrlParser {
    override fun parseBoardId(url: HttpUrl): String? {
        return url.queryParameter("bsn")
    }

    override fun parseThreadId(url: HttpUrl): String? {
        return url.queryParameter("snA")
    }

    override fun parsePostId(url: HttpUrl): String? {
        return url.queryParameter("sn")
    }

    override fun parsePage(url: HttpUrl): Int {
        return url.queryParameter("page")?.toInt() ?: 1
    }

    override fun hasBoardId(url: HttpUrl): Boolean {
        return parseBoardId(url) != null
    }

    override fun hasThreadId(url: HttpUrl): Boolean {
        return parseThreadId(url) != null
    }

    override fun hasPostId(url: HttpUrl): Boolean {
        return parsePostId(url) != null
    }

    override fun hasPage(url: HttpUrl): Boolean {
        return parsePage(url) != null
    }

    override fun parseThreadUrl(url: HttpUrl): String {
        if (
            !hasBoardId(url) ||
            !hasThreadId(url)
        ) throw IllegalArgumentException()

        val builder = url.newBuilder()
        builder.removeAllQueryParameters("sn")
        builder.removeAllQueryParameters("page")
        return builder.build().toUrl().toString()
    }
}