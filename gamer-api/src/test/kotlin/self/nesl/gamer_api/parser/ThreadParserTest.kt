package self.nesl.gamer_api.parser

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.gamer_api.loadFile
import self.nesl.gamer_api.request.RequestBuilderImpl

internal class ThreadParserTest {

    @Test
    fun `Test ThreadParser expect successful`() {
        val parser = ThreadParser(PostParser(UrlParserImpl()), UrlParserImpl(), RequestBuilderImpl())
        val list = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/ThreadPage.html")),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/C.php?bsn=60076&snA=4166175").build(),
        )
        assertEquals(
            listOf("夕立醬的潛水魚雷", "路過的雞蛋糕", "哈哈哈哈哈", "玫瑰捅你眼", "chi", "我是佑佑"),
            list.map { it.posterName }.subList(0, 6),
        )

        assertEquals(18, list.size)
    }
}