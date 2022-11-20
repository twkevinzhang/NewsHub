package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost

class BoardParser(
    private val newsParser: NewsParser,
): Parser<List<GNews>> {
    override fun parse(source: Element, url: String): List<GNews> {
        val newsList = source.select("tr.b-list__row.b-list-item.b-imglist-item:not(.b-list__row--sticky)")
        return newsList.map {
            val href = source.selectFirst("a[href^=\"C.php?bsn=\"]").attr("href")
            val threadUrl = url.toHttpUrl().newBuilder()
                .replaceAfterHost("/$href")
                .removeAllQueryParameters("tnum")
                .build()
            newsParser.parse(it, threadUrl.toString())
        }
    }

    private fun HttpUrl.Builder.replaceAfterHost(after: String): HttpUrl.Builder {
        require(after.startsWith("/")){ "after should be starts with /: $after" }
        val url = encodedPath("/").encodedQuery(null).encodedFragment(null)
        return (url.toString().substringBeforeLast("/") + after).toHttpUrl().newBuilder()
    }
}