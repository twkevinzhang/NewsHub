package self.nesl.hub_server.data.news_thread

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.Paragraph

internal class CommentExtensionsTest {

    @Test
    fun `Test CommentList filterReplyToIs extensions with correct target expect successful`() {
        val comment1 = object: Comment {
            override val id = "D"
            override val url = "https://example.org?id=D"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
                Paragraph.ReplyTo("C"),
            )
        }

        val comment2 = object: Comment {
            override val id = "C"
            override val url = "https://example.org?id=C"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
            )
        }

        val comment3 = object: Comment {
            override val id = "B"
            override val url = "https://example.org?id=B"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
            )
        }

        assertEquals(2, listOf(comment1, comment2, comment3).filterReplyToIs("B").size)
    }
}