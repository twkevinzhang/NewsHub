package self.nesl.gamer_api.parser

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.gamer_api.loadFile

internal class ThreadParserTest {

    @Test
    fun `Test ThreadParser expect successful`() {
        val parser = ThreadParser(PostParser(UrlParserImpl()))
        val pair = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/ThreadPage.html")),
            "https://forum.gamer.com.tw/C.php?bsn=60076&snA=4166175",
        )
        assertEquals(17, pair.second.size)
    }
}