package self.nesl.komica_api.parser._2cat

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class _2catPostParserTest {

    @Test
    fun `Test _2catPostParser expect successful`() {
        val parser = _2catPostParser(_2catUrlParser(), _2catPostHeadParser(_2catUrlParser()))
        val post = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/org/2cat/ReplyPost.html")),
            "https://2cat.org/granblue/?res=963#r23587",
        )
        assertEquals("23587", post.id)
    }
}