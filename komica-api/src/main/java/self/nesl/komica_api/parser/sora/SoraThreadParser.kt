package self.nesl.komica_api.parser.sora

import org.jsoup.nodes.Element
import self.nesl.komica_api.installThreadTag
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.parser.Parser
import self.nesl.komica_api.replyFor

class SoraThreadParser(
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
        val threads = source.selectFirst("#threads").installThreadTag().select("div.thread")
        val posts = threads.select("div.reply").map { reply_ele ->
            val postId = reply_ele.attr("id").substring(1) // #r12345678
            val postUrl = "$url#r$postId"
            postParser.parse(reply_ele, postUrl)
        }
        setRepliesCount(posts)
        return posts
    }

    private fun setRepliesCount(posts: List<KPost>) {
        for (post in posts) {
            post.replies = posts.replyFor(post.id).size
        }
    }
}

