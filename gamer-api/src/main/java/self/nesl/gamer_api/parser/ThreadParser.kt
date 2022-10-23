package self.nesl.gamer_api.parser

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GPost

class ThreadParser(
    private val postParser: Parser<GPost>,
    private val urlParser: UrlParser,
): Parser<List<GPost>> {
    override fun parse(source: Element, url: String): List<GPost> {
        val httpUrl = url.toHttpUrl()
        val requestPage = urlParser.parsePage(httpUrl)
        val threadUrl = urlParser.parseThreadUrl(httpUrl)
        val responsePage = currentPage(source)
        if (requestPage != responsePage) {
            return emptyList()
        }
        return listOf(parseHead(source, threadUrl)).plus(parseReplies(source, url))
    }

    private fun currentPage(source: Element): Int {
        val page = source.selectFirst("p.BH-pagebtnA a.pagenow")?.text()
        return page?.toInt() ?: 1
    }

    private fun parseHead(source: Element, threadUrl: String): GPost {
        val section = source.selectFirst("section.c-section[id^=\"post_\"]")
        val postId = section.id().replace("post_", "")
        val postUrl = threadUrl.plus("&sn=$postId")
        return postParser.parse(section, postUrl)
    }

    private fun parseReplies(source: Element, threadUrl: String): List<GPost> {
        val allPosts = source.select("section.c-section[id^=\"post_\"]")
        return allPosts.map { parseHead(it, threadUrl) }.subList(1, allPosts.size)
    }
}

