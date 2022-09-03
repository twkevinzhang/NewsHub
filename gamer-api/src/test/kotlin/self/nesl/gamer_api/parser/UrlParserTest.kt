package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UrlParserTest {

    @Test
    fun `Test parsePostId expect successful`() {
        val parser = UrlParserImpl()
        val postId = parser.parsePostId(HttpUrl.parse("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245")!!)
        assertEquals("87956245", postId)
    }

    @Test
    fun `Test parseBoardId expect successful`() {
        val parser = UrlParserImpl()
        val boardId = parser.parseBoardId(HttpUrl.parse("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245")!!)
        assertEquals("60076", boardId)
    }

    @Test
    fun `Test parseThreadId expect successful`() {
        val parser = UrlParserImpl()
        val threadId = parser.parseThreadId(HttpUrl.parse("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245")!!)
        assertEquals("7398008", threadId)
    }
}