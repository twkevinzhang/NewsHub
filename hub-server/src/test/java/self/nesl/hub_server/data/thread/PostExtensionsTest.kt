package self.nesl.hub_server.data.thread

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.Paragraph

internal class PostExtensionsTest {

    @Test
    fun `Test RePostList filterReplyToIs extensions with correct target expect successful`() {
        val post1 = object: Post {
            override val id = "D"
            override val url = "https://example.org?id=D"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
                Paragraph.ReplyTo("C"),
            )
        }

        val post2 = object: Post {
            override val id = "C"
            override val url = "https://example.org?id=C"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
            )
        }

        val post3 = object: Post {
            override val id = "B"
            override val url = "https://example.org?id=B"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
            )
        }

        assertEquals(2, listOf(post1, post2, post3).parentIs("B").size)
    }
}