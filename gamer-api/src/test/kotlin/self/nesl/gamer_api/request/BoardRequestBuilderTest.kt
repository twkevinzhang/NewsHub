package self.nesl.gamer_api.request

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BoardRequestBuilderTest {

    @Test
    fun `Test setPageReq with url expect successful`() {
        val req = BoardRequestBuilder()
            .url("https://forum.gamer.com.tw/B.php?bsn=60076")
            .setPageReq(1)
            .build()
        assertEquals("https://forum.gamer.com.tw/B.php?bsn=60076&page=1".toHttpUrl(), req.url)
    }

    @Test
    fun `Test setPageReq with url with page_num expect successful`() {
        val req = BoardRequestBuilder()
            .url("https://forum.gamer.com.tw/B.php?page=2&bsn=60076")
            .setPageReq(1)
            .build()
        assertEquals("https://forum.gamer.com.tw/B.php?bsn=60076&page=1".toHttpUrl(), req.url)
    }
}