package self.nesl.komica_api.request.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SoraBoardRequestBuilderTest {

    @Test
    fun `Test setPageReq with url expect successful`() {
        val req = SoraBoardRequestBuilder()
            .url("https://gaia.komica.org/00")
            .setPageReq(1)
            .build()
        assertEquals(
            "https://gaia.komica.org/00/1.htm".toHttpUrl(),
            req.url
        )
    }

    @Test
    fun `Test setPageReq with url with page_num expect successful`() {
        val req = SoraBoardRequestBuilder()
            .url("https://gaia.komica.org/00/2.htm")
            .setPageReq(1)
            .build()
        assertEquals(
            "https://gaia.komica.org/00/1.htm".toHttpUrl(),
            req.url
        )
    }
}