package dev.zlong.gamer_api.parser

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import dev.zlong.gamer_api.model.GNews
import dev.zlong.gamer_api.request.RequestBuilder
import dev.zlong.gamer_api.toResponseBody

class BoardParser(
    private val newsParser: NewsParser,
    private val requestBuilder: RequestBuilder,
): Parser<List<GNews>> {
    override fun parse(body: ResponseBody, req: Request): List<GNews> {
        val source = Jsoup.parse(body.string())
        val newsList = source.select("tr.b-list__row.b-list-item.b-imglist-item:not(.b-list__row--sticky)")
        return newsList.map {
            val href = it.selectFirst("a[href^=\"C.php?bsn=\"]").attr("href")
            val threadUrl = req.url.newBuilder()
                .replaceAfterHost("/$href")
                .removeAllQueryParameters("tnum")
                .build()
            newsParser.parse(it.toResponseBody(), requestBuilder.setUrl(threadUrl).build())
        }
    }

    private fun HttpUrl.Builder.replaceAfterHost(after: String): HttpUrl.Builder {
        require(after.startsWith("/")){ "after should be starts with /: $after" }
        val url = encodedPath("/").encodedQuery(null).encodedFragment(null)
        return (url.toString().substringBeforeLast("/") + after).toHttpUrl().newBuilder()
    }
}