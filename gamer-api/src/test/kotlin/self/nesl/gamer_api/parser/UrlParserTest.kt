package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UrlParserTest {

    @Test
    fun `Test parseBoardId expect successful`() {
        val parser = UrlParserImpl()
        val boardId = parser.parseBoardId("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245".toHttpUrl())
        assertEquals("60076", boardId)
    }

    @Test
    fun `Test parseThreadId expect successful`() {
        val parser = UrlParserImpl()
        val threadId = parser.parseThreadId("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245".toHttpUrl())
        assertEquals("7398008", threadId)
    }

    @Test
    fun `Test parsePostId expect successful`() {
        val parser = UrlParserImpl()
        val postId = parser.parsePostId("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245".toHttpUrl())
        assertEquals("87956245", postId)
    }

    @Test
    fun `Test parsePage expect successful`() {
        val parser = UrlParserImpl()
        val page = parser.parsePage("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245&page=2".toHttpUrl())
        assertEquals(2, page)
    }

    @Test
    fun `Test hasPostId expect successful`() {
        val parser = UrlParserImpl()
        val hasPostId = parser.hasPostId("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245".toHttpUrl())
        assertEquals(true, hasPostId)
    }

    @Test
    fun `Test hasPage expect successful`() {
        val parser = UrlParserImpl()
        val hasPage = parser.hasPage("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245&page=2".toHttpUrl())
        assertEquals(true, hasPage)
    }

    @Test
    fun `Test parseThreadUrl expect successful`() {
        val parser = UrlParserImpl()
        val url = parser.parseThreadUrl("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008&sn=87956245&page=2".toHttpUrl())
        assertEquals("https://forum.gamer.com.tw/C.php?bsn=60076&snA=7398008", url)
    }
}