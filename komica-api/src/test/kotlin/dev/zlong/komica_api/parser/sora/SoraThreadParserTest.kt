package dev.zlong.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class SoraThreadParserTest {

    @Test
    fun `Test parse thread with 綜合 ThreadPage html expect successful`() {
        val parser = SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val pair = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/ThreadPage.html")),
            "https://sora.komica.org/00/pixmicat.php?res=25214959",
        )
        assertEquals(49, pair.size)
    }

    @Test
    fun `Test parse thread with 2cat ThreadPage html expect successful`() {
        val parser = SoraThreadParser(SoraPostParser(SoraUrlParser(), SoraPostHeadParser()))
        val pair = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/2cat/ThreadPage.html")),
            "https://2cat.komica.org/~tedc21thc/new/pixmicat.php?res=4003068",
        )
        assertEquals(37, pair.size)
    }
}