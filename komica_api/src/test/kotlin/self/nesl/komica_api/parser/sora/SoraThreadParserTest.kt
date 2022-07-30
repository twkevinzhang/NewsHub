package self.nesl.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraThreadParserTest {

    @Test
    fun `Test SoraThreadParser expect successful`() {
        val parser = SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val pair = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/sora/ThreadPage.html")),
            "https://sora.komica.org/00/pixmicat.php?res=25214959",
        )
        assertEquals(48, pair.second.size)
    }
}