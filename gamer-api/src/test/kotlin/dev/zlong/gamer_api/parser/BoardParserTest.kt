package dev.zlong.gamer_api.parser

import okhttp3.ResponseBody.Companion.toResponseBody
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.gamer_api.loadFile
import dev.zlong.gamer_api.request.RequestBuilderImpl

internal class BoardParserTest {

    @Test
    fun `Test BoardParser expect successful`() {
        val parser = BoardParser(NewsParser(), RequestBuilderImpl())
        val newsList = parser.parse(
            loadFile("./src/test/html/BoardPage.html")!!.toResponseBody(),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/C.php?bsn=60076").build(),
        )
        assertEquals(23, newsList.size)
    }
}