package dev.zlong.komica_api.parser._2cat

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class _2catBoardParserTest {

    @Test
    fun `Test _2catBoardParser expect successful`() {
        val parser = _2catBoardParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/2cat/BoardPage.html")),
            "https://2cat.org/granblue",
        )
        assertEquals(8, posts.size)
    }
}