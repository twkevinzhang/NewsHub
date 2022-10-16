package self.nesl.komica_api.parser._2cat

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.loadFile

internal class _2catUrlParserTest {

    @Test
    fun `Test _2catUrlParser expect successful`() {
        val parser = _2catUrlParser()
        val postId = parser.parsePostId("https://2cat.org/granblue/?res=23210#r23211".toHttpUrl())
        assertEquals("23211", postId)
    }
}