package self.nesl.hub_server.data.news_thread

import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.trySubstring

interface RePost {

    /**
     * Returns the id in the discussion thread.
     */
    val id: String

    val url: String

    val content: List<Paragraph>
}

fun List<RePost>.parentIs(parentId: String): List<RePost> {
    return this.filter { it.parent().contains(parentId) }
}

fun RePost.parent(): List<String> {
    return content
        .filterIsInstance<Paragraph.ReplyTo>()
        .map { paragraph -> paragraph.id }
}

fun RePost.toText(): String {
    return content
        .filterIsInstance<Paragraph.Text>()
        .first()
        .content
        .trySubstring(0..5)
}