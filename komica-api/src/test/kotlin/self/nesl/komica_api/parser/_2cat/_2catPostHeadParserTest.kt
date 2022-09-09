package self.nesl.komica_api.parser._2cat

import okhttp3.HttpUrl
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile
import self.nesl.komica_api.parser.sora.SoraPostHeadParser

internal class _2catPostHeadParserTest {

    @Test
    fun `Test parse title expect successful`() {
        val parser = _2catPostHeadParser(_2catUrlParser())
        val title = parser.parseTitle(
            Jsoup.parse(loadFile("./src/test/html/_2cat/ReplyPost.html")),
            HttpUrl.parse("https://2cat.org/granblue/?res=963#r23587")!!
        )
        assertEquals("無標題", title)
    }

    @Test
    fun `Test parse created at expect successful`() {
        val parser = _2catPostHeadParser(_2catUrlParser())
        val time = parser.parseCreatedAt(
            Jsoup.parse(loadFile("./src/test/html/_2cat/ReplyPost.html")),
            HttpUrl.parse("https://2cat.org/granblue/?res=963#r23587")!!
        )
        assertEquals(1638198600000L, time)
    }
}