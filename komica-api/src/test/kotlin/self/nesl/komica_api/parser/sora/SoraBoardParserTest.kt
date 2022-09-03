package self.nesl.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraBoardParserTest {

    @Test
    fun `Test SoraBoardParser expect successful`() {
        val parser = SoraBoardParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val posts = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/sora/BoardPage.html")),
            "https://sora.komica.org/00",
        )
        assertEquals(15, posts.size)
    }
}