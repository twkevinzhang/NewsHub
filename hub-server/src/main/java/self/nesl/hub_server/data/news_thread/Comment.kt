package self.nesl.hub_server.data.news_thread

import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.trySubstring

interface Comment {

    /**
     * Returns the id in the discussion thread.
     */
    val id: String

    val url: String

    val content: List<Paragraph>
}

fun List<Comment>.filterReplyToIs(commentId: String): List<Comment> {
    return this.filter { it.replyTo().contains(commentId) }
}

fun Comment.replyTo(): List<String> {
    return content
        .filterIsInstance<Paragraph.ReplyTo>()
        .map { paragraph -> paragraph.content }
}

fun Comment.toText(): String {
    return content
        .filterIsInstance<Paragraph.Text>()
        .first()
        .content
        .trySubstring(0..5)
}