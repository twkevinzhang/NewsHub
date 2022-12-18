package dev.zlong.komica_api.parser._2cat

import org.jsoup.nodes.Element
import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.parser.Parser
import dev.zlong.komica_api.parser.sora.SoraBoardParser
import dev.zlong.komica_api.parser.sora.SoraBoardParser.Companion.parseReplyCount
import dev.zlong.komica_api.parser.sora.SoraPostParser

class _2catBoardParser(
    private val postParser: Parser<KPost>,
): Parser<List<KPost>> {
    override fun parse(source: Element, url: String): List<KPost> {
        val threads = source.select("div.threadpost")
        return threads.map { thread ->
            val threadpost = thread.selectFirst("div.threadpost")
            val postId = threadpost.attr("id").substring(1)
            val postUrl = "$url/?res=$postId"
            val post = postParser.parse(threadpost, postUrl)
            post.copy(replies = parseReplyCount(thread))
        }
    }
}

