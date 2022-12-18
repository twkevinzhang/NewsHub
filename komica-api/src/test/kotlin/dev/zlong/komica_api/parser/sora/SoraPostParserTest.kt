package dev.zlong.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.komica_api.loadFile

internal class SoraPostParserTest {

    @Test
    fun `Test parse post with 綜合 ReplyPost html expect successful`() {
        val parser = SoraPostParser(SoraUrlParser(), SoraPostHeadParser())
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/ReplyPost.html")),
            "https://sora.komica.org/00/pixmicat.php?res=25208017",
        )
        assertEquals("25208017", post.id)
    }

    @Test
    fun `Test parse post with 2cat ReplyPost html expect successful`() {
        val parser = SoraPostParser(SoraUrlParser(), SoraPostHeadParser())
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/2cat/ReplyPost.html")),
            "https://2cat.komica.org/~tedc21thc/new/pixmicat.php?res=4003068",
        )
        assertEquals("4003068", post.id)
    }
}