package self.nesl.hub_server.data.post

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.Host

internal class PostExtensionsTest {

    @Test
    fun `Test PostList filterReplyToIs extensions with correct target expect successful`() {
        val post1 = object: Post {
            override val id = "D"
            override val url = "https://gaia.komica.org/00"
            override val boardUrl = "https://gaia.komica.org/00/pixmicat.php?res=29683783"
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
        }

        val post2 = object: Post {
            override val id = "C"
            override val url = "https://gaia.komica.org/00"
            override val boardUrl = "https://gaia.komica.org/00/pixmicat.php?res=29683783"
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
        }

        val post3 = object: Post {
            override val id = "B"
            override val url = "https://gaia.komica.org/00"
            override val boardUrl = "https://gaia.komica.org/00/pixmicat.php?res=29683783"
            override val title = "title"
            override val createdAt = 0L
            override val poster = "poster"
            override val visits = 0
            override val replies = 0
            override val readAt = 0
            override val content = listOf(
                Paragraph.ReplyTo("A"),
            )
        }

        assertEquals(2, listOf(post1, post2, post3).parentIs("B").size)
    }
}