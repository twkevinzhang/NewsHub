package self.nesl.komica_api.parser.sora

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraPostParserTest {

    @Test
    fun `Test SoraPostParser expect successful`() {
        val parser = SoraPostParser(SoraUrlParser(), SoraPostHeadParser())
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/komica/sora/ReplyPost.html")),
            "https://sora.komica.org/00/pixmicat.php?res=25208017",
        )
        assertEquals("25208017", post.id)
    }
}