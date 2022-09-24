package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraUrlParserTest {

    @Test
    fun `Test parse 綜合 board url expect successful`() {
        val parser = SoraUrlParser()
        val postId = parser.parsePostId("https://sora.komica.org/00/pixmicat.php?res=25208017".toHttpUrlOrNull()!!)
        assertEquals("25208017", postId)
    }

    @Test
    fun `Test parse 2cat board url expect successful`() {
        val parser = SoraUrlParser()
        val postId = parser.parsePostId("http://2cat.komica.org/~tedc21thc/new/pixmicat.php?res=4002572".toHttpUrlOrNull()!!)
        assertEquals("4002572", postId)
    }
}