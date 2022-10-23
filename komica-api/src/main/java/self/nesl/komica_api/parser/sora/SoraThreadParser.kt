package self.nesl.komica_api.parser.sora

import org.jsoup.nodes.Element
import self.nesl.komica_api.installThreadTag
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.filterReplyToIs
import self.nesl.komica_api.parser.Parser

class SoraThreadParser(
    private val postParser: Parser<KPost>,
): Parser<List<KPost>> {
    override fun parse(source: Element, url: String): List<KPost> {
        return listOf(parseHead(source, url)).plus(parseReplies(source, url))
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
            post.replies = posts.filterReplyToIs(post.id).size
        }
    }
}

