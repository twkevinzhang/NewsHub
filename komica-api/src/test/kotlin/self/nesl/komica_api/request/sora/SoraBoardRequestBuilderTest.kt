package self.nesl.komica_api.request.sora

import okhttp3.HttpUrl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SoraBoardRequestBuilderTest {

    @Test
    fun `Test setPageReq with url expect successful`() {
        val req = SoraBoardRequestBuilder()
            .url("https://sora.komica.org/00")
            .setPageReq(1)
            .build()
        assertEquals(HttpUrl.parse("https://sora.komica.org/00/pixmicat.php?page_num=1"), req.url())
    }

    @Test
    fun `Test setPageReq with url with page_num expect successful`() {
        val req = SoraBoardRequestBuilder()
            .url("https://sora.komica.org/00/pixmicat.php?page_num=2")
            .setPageReq(1)
            .build()
        assertEquals(HttpUrl.parse("https://sora.komica.org/00/pixmicat.php?page_num=1"), req.url())
    }
}