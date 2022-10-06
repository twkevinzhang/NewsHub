package self.nesl.komica_api.parser._2cat

import org.jsoup.nodes.Element
import self.nesl.komica_api.installThreadTag
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.parser.Parser

class _2catThreadParser(
    private val postParser: Parser<KPost>,
): Parser<Pair<KPost, List<KPost>>> {
    override fun parse(source: Element, url: String): Pair<KPost, List<KPost>> {
        return Pair(
            parseHead(source, url),
            parseReplies(source, url),
        )
    }

    private fun parseHead(source: Element, url: String): KPost {
        return postParser.parse(source.selectFirst("div.threadpost"), url)
    }

    private fun parseReplies(source: Element, url: String): List<KPost> {
        val posts = source.select("div[class=\"reply\"][id^='r']").map { reply_ele ->
            val replyId = reply_ele.id() // r123456
            val postUrl = "$url#$replyId"
            postParser.parse(reply_ele, postUrl)
        }
        return posts
    }
}

