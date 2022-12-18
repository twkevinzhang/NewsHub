package dev.zlong.gamer_api.parser

import okhttp3.ResponseBody.Companion.toResponseBody
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import dev.zlong.gamer_api.loadFile
import dev.zlong.gamer_api.request.RequestBuilderImpl

internal class CommentListParserTest {

    @Test
    fun `Test CommentListParser expect successful`() {
        val parser = CommentListParser(RequestBuilderImpl())
        val list = parser.parse(
            loadFile("./src/test/json/CommentList.json")!!.toResponseBody(),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/ajax/moreCommend.php?bsn=60076&snB=89090465").build(),
        )
        assertEquals(
            listOf("3307356", "3307326"),
            list.map { it.sn }.subList(0, 2),
        )

        assertEquals(464, list.size)
    }
}