package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class SoraUrlParserTest {

    @Test
    fun `Test SoraUrlParser expect successful`() {
        val parser = SoraUrlParser()
        val postId = parser.parsePostId("https://sora.komica.org/00/pixmicat.php?res=25208017".toHttpUrlOrNull()!!)
        assertEquals("25208017", postId)
    }
}