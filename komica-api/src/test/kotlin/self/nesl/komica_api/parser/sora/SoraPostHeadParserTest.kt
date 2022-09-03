package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraPostHeadParserTest {

    @Test
    fun `Test parse title expect successful`() {
        val parser = SoraPostHeadParser()
        val title = parser.parseTitle(
            Jsoup.parse(loadFile("./src/test/html/sora/HeadPost.html")),
            HttpUrl.parse("https://sora.komica.org/00/pixmicat.php?res=25208017")!!
        )
        assertEquals("無題", title)
    }

    @Test
    fun `Test parse created at expect successful`() {
        val parser = SoraPostHeadParser()
        val time = parser.parseCreatedAt(
            Jsoup.parse(loadFile("./src/test/html/sora/HeadPost.html")),
            HttpUrl.parse("https://sora.komica.org/00/pixmicat.php?res=25208017")!!
        )
        assertEquals(1638460800000L, time)
    }
}