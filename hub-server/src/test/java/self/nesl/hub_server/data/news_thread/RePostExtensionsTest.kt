package self.nesl.hub_server.data.news_thread

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.Paragraph

internal class RePostExtensionsTest {

    @Test
    fun `Test RePostList filterReplyToIs extensions with correct target expect successful`() {
        val rePost1 = object: RePost {
            override val id = "D"
            override val url = "https://example.org?id=D"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
                Paragraph.ReplyTo("C"),
            )
        }

        val rePost2 = object: RePost {
            override val id = "C"
            override val url = "https://example.org?id=C"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
            )
        }

        val rePost3 = object: RePost {
            override val id = "B"
            override val url = "https://example.org?id=B"
            override val content = listOf(
                Paragraph.ReplyTo("A"),
            )
        }

        assertEquals(2, listOf(rePost1, rePost2, rePost3).parentIs("B").size)
    }
}