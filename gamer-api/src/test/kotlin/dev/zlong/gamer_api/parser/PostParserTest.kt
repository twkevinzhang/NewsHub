package dev.zlong.gamer_api.parser

import okhttp3.ResponseBody.Companion.toResponseBody
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.gamer_api.loadFile
import dev.zlong.gamer_api.request.RequestBuilderImpl

internal class PostParserTest {

    @Test
    fun `Test PostParser expect successful`() {
        val parser = PostParser(UrlParserImpl())
        val post = parser.parse(
            loadFile("./src/test/html/Post.html")!!.toResponseBody(),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/C.php?bsn=60076&snA=4166175&sn=46104650").build(),
        )
        assertEquals("46104650", post.id)
    }
}