package dev.zlong.komica_api.parser.sora

import org.jsoup.nodes.Element
import dev.zlong.komica_api.installThreadTag
import dev.zlong.komica_api.model.KPost
import dev.zlong.komica_api.parser.Parser
import dev.zlong.komica_api.request.sora.SoraRequestBuilder
import dev.zlong.komica_api.toResponseBody
import okhttp3.Request
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import java.lang.NullPointerException

class SoraBoardParser(
    private val postParser: Parser<KPost>,
    private val threadRequestBuilder: SoraRequestBuilder,
): Parser<List<KPost>> {
    override fun parse(res: ResponseBody, req: Request): List<KPost> {
        val source = Jsoup.parse(res.string())
        val url = req.url
        // get post secret name
//        String fsub = getElement().selectFirst("#fsub").attr("name");
//        String fcom = getElement().selectFirst("#fcom").attr("name");
        val threads = source.selectFirst("#threads").installThreadTag().select("div.thread")
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

    companion object {
        fun parseReplyCount(thread: Element): Int {
            var replyCount = 0
            try {
                replyCount = thread.selectFirst("span.warn_txt2").text()
                    .replace("\\D".toRegex(), "")
                    .toInt()
            } catch (ignored: NullPointerException) { }
            replyCount += thread.getElementsByClass("reply").size
            return replyCount
        }
    }
}

