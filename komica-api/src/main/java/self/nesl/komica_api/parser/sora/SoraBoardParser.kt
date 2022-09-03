package self.nesl.komica_api.parser.sora

import org.jsoup.nodes.Element
import self.nesl.komica_api.installThreadTag
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.parser.Parser
import java.lang.NullPointerException

class SoraBoardParser(
    private val postParser: Parser<KPost>,
): Parser<List<KPost>> {
    override fun parse(source: Element, url: String): List<KPost> {
        // get post secret name
//        String fsub = getElement().selectFirst("#fsub").attr("name");
//        String fcom = getElement().selectFirst("#fcom").attr("name");
        val threads = source.selectFirst("#threads").installThreadTag().select("div.thread")
        return threads.map { thread ->
            val threadpost = thread.selectFirst("div.threadpost")
            val postId = threadpost.attr("id").substring(1)
            val postUrl = "$url/pixmicat.php?res=$postId"
            val post = postParser.parse(threadpost, postUrl)
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

