package self.nesl.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraBoardParserTest {

    @Test
    fun `Test parse posts with 綜合 BoardPage html expect successful`() {
        val parser = SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/BoardPage.html")),
            "https://sora.komica.org/00",
        )
        assertEquals(15, posts.size)
    }

    @Test
    fun `Test parse posts with 2cat BoardPage html expect successful`() {
        val parser = SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/2cat/BoardPage.html")),
            "https://2cat.komica.org/~tedc21thc/new",
        )
        assertEquals(11, posts.size)
    }
}