package self.nesl.komica_api.parser._2cat

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class _2catThreadParserTest {

    @Test
    fun `Test _2catThreadParser expect successful`() {
        val parser = _2catThreadParser(_2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser())))
        val pair = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/_2cat/ThreadPage.html")),
            "https://2cat.org/granblue/?res=963",
        )
        assertEquals(48, pair.second.size)
    }
}