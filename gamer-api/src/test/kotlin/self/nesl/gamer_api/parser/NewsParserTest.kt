package self.nesl.gamer_api.parser

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.gamer_api.loadFile
import self.nesl.gamer_api.request.RequestBuilderImpl

internal class NewsParserTest {

    @Test
    fun `Test NewsParser expect successful`() {
        val parser = NewsParser()
        val news = parser.parse(
            Jsoup.parse(loadFile("./src/test/html/News.html")),
            RequestBuilderImpl().url("https://forum.gamer.com.tw/C.php?bsn=60076").build(),
        )
        assertEquals(news.title, "【問題】吃滷味/麻辣燙只點麵、肉片、菜，然後不點火鍋料跟豆製食品的人在想什麼啊的人")
    }
}