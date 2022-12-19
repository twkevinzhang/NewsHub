package dev.zlong.komica_api.parser._2cat

import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.parser.Parser
import dev.zlong.komica_api.parser.sora.SoraBoardParser.Companion.parseReplyCount
import dev.zlong.komica_api.request._2cat._2catRequestBuilder
import dev.zlong.komica_api.toResponseBody
import okhttp3.Request
import okhttp3.ResponseBody
import org.jsoup.Jsoup

class _2catBoardParser(
    private val postParser: Parser<KPost>,
    private val threadRequestBuilder: _2catRequestBuilder,
): Parser<List<KPost>> {
    override fun parse(res: ResponseBody, req: Request): List<KPost> {
        val source = Jsoup.parse(res.string())
        val url = req.url
        val threads = source.select("div.threadpost")
        return threads.map { thread ->
            val threadpost = thread.selectFirst("div.threadpost")
            val postId = threadpost.attr("id").substring(1)
            val post = postParser.parse(
                threadpost.toResponseBody(),
                threadRequestBuilder.setUrl(url).setRes(postId).build(),
            )
            post.copy(replies = parseReplyCount(thread))
        }
    }
}

