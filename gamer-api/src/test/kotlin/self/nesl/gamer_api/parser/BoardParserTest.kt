package self.nesl.gamer_api.parser

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.gamer_api.loadFile
import self.nesl.gamer_api.request.RequestBuilderImpl

internal class BoardParserTest {

    @Test
    fun `Test BoardParser expect successful`() {
        val parser = BoardParser(NewsParser(), RequestBuilderImpl())
        val newsList = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/BoardPage.html")),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/C.php?bsn=60076").build(),
        )
        assertEquals(23, newsList.size)
    }
}