package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GPost

class ThreadParser(
    private val postParser: Parser<GPost>,
): Parser<Pair<GPost, List<GPost>>> {
    override fun parse(source: Element, url: String): Pair<GPost, List<GPost>> {
        return Pair(
            parseHead(source, url),
            parseReplies(source, url),
        )
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

