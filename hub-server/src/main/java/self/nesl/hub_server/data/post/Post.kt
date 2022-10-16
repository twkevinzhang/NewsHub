package self.nesl.hub_server.data.post

import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news.News
import self.nesl.hub_server.trySubstring

interface Post: News {

    /**
     * Returns the id in the discussion thread.
     */
    val id: String

    /**
     * Returns the content and it's contain the reply target [Paragraph.ReplyTo]
     */
    val content: List<Paragraph>

    val title: String
}

fun List<Post>.parentIs(parentId: String): List<Post> {
    return this.filter { it.parent().contains(parentId) }
}

fun Post.parent(): List<String> {
    return content
        .filterIsInstance<Paragraph.ReplyTo>()
        .map { paragraph -> paragraph.id }
}

fun Post.toText(): String {
    return content
        .filterIsInstance<Paragraph.Text>()
        .first()
        .content
        .trySubstring(0..5)
}