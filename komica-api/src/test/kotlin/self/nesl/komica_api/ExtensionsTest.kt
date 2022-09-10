package self.nesl.komica_api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.model.KPostBuilder
import self.nesl.komica_api.model.KReplyTo

internal class ExtensionsTest {

    @Test
    fun `Test withProtocol extension expect successful`() =
        assertEquals("https://www.google.com", "//www.google.com".withHttps())

    @Test
    fun `Test withProtocol extension with correct baseUrl expect successful`() =
        assertEquals("https://www.google.com/search", "/search".withHttps("https://www.google.com"))

    @Test
    fun `Test replaceJpnWeekday extension expect successful`() =
        assertEquals("Mon Tue", "月 火".replaceJpnWeekday())

    @Test
    fun `Test replaceChiWeekday extension expect successful`() =
        assertEquals("Mon Tue", "一 二".replaceChiWeekday())

    @Test
    fun `Test toMillTimestamp extension expect successful`() =
        assertEquals(1662795827333L, "2022/09/10(Sat) 15:43:47.333".toMillTimestamp())

    @Test
    fun `Test toMillTimestamp with years with only two digits extension expect successful`() =
        assertEquals(1662795827000L, "22/09/10(Sat) 15:43:47".toMillTimestamp())

    @Test
    fun `Test replyFor extension expect successful`() {
        val post1 = KPostBuilder().setContent(listOf(
            KReplyTo("A"),
            KReplyTo("B"),
            KReplyTo("C"),
        )).build()

        val post2 = KPostBuilder().setContent(listOf(
            KReplyTo("A"),
            KReplyTo("B"),
        )).build()

        val post3 = KPostBuilder().setContent(listOf(
            KReplyTo("A"),
        )).build()

        assertEquals(2, listOf(post1, post2, post3).replyFor("B").size)
    }
}

