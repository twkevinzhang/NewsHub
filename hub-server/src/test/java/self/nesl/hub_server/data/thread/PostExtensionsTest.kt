package self.nesl.hub_server.data.thread

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.post.Host

internal class PostExtensionsTest {

    @Test
    fun `Test PostList filterReplyToIs extensions with correct target expect successful`() {
        val post1 = object: Post {
            override val id = "D"
            override val url = "https://example.org?id=D"
            override val host = Host.KOMICA
            override val title = "title"
            override val createdAt = 0L
            override val poster = "poster"
            override val visits = 0
            override val replies = 0
            override val readAt = 0
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
                Paragraph.ReplyTo("C"),
            )
            override val favorite = "favorite"
        }

        val post2 = object: Post {
            override val id = "C"
            override val url = "https://example.org?id=C"
            override val host = Host.KOMICA
            override val title = "title"
            override val createdAt = 0L
            override val poster = "poster"
            override val visits = 0
            override val replies = 0
            override val readAt = 0
            override val content = listOf(
                Paragraph.ReplyTo("A"),
                Paragraph.ReplyTo("B"),
            )
            override val favorite = "favorite"
        }

        val post3 = object: Post {
            override val id = "B"
            override val url = "https://example.org?id=B"
            override val host = Host.KOMICA
            override val title = "title"
            override val createdAt = 0L
            override val poster = "poster"
            override val visits = 0
            override val replies = 0
            override val readAt = 0
            override val content = listOf(
                Paragraph.ReplyTo("A"),
            )
            override val favorite = "favorite"
        }

        assertEquals(2, listOf(post1, post2, post3).parentIs("B").size)
    }
}