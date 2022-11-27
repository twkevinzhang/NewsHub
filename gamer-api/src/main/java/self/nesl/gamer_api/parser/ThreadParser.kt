package self.nesl.gamer_api.parser

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GPost
import self.nesl.gamer_api.request.RequestBuilder

class ThreadParser(
    private val postParser: Parser<GPost>,
    private val urlParser: UrlParser,
    private val requestBuilder: RequestBuilder,
): Parser<List<GPost>> {
    override fun parse(source: Element, req: Request): List<GPost> {
        val currentPage = currentPage(source)
        return listOf(parseHead(source, req, currentPage)).plus(parseReplies(source, req, currentPage))
    }

    private fun currentPage(source: Element): Int {
        val page = source.selectFirst("p.BH-pagebtnA a.pagenow")?.text()
        return page?.toInt() ?: 1
    }

    private fun parseHead(source: Element, req: Request, responsePage: Int): GPost {
        val section = source.selectFirst("section.c-section[id^=\"post_\"]")
        val postId = section.id()
        val postReq = requestBuilder
            .url(req.url.toString().plus("&sn=$postId"))
            .setPageReq(responsePage)
            .build()
        return postParser.parse(section, postReq)
    }

    private fun parseReplies(source: Element, req: Request, responsePage: Int): List<GPost> {
        val allPosts = source.select("section.c-section[id^=\"post_\"]")
        return allPosts.map { parseHead(it, req, responsePage) }.subList(1, allPosts.size)
    }
}

