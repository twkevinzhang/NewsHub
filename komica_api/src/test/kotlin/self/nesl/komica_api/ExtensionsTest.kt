package self.nesl.komica_api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KPostBuilder
import self.nesl.komica_api.model.Paragraph
import self.nesl.komica_api.model.ParagraphType

internal class ExtensionsTest {

    @Test
    fun `Test withProtocol extension expect successful`() =
        assertEquals("http://www.google.com", "//www.google.com".withProtocol())

    @Test
    fun `Test withProtocol extension with correct baseUrl expect successful`() =
        assertEquals("https://www.google.com/search", "/search".withProtocol("https://www.google.com"))

    @Test
    fun `Test replaceJpnWeekday extension expect successful`() =
        assertEquals("Mon Tue", "月 火".replaceJpnWeekday())

    @Test
    fun `Test replaceChiWeekday extension expect successful`() =
        assertEquals("Mon Tue", "一 二".replaceChiWeekday())

    @Test
    fun `Test toTimestamp extension expect successful`() =
        assertEquals(1659259281333L, "2022/07/31(Sun) 17:21:21.333".toTimestamp())

    @Test
    fun `Test replyFor extension expect successful`() {
        val post1 = KPostBuilder().setContent(listOf(
            Paragraph("A", ParagraphType.REPLY_TO),
            Paragraph("B", ParagraphType.REPLY_TO),
            Paragraph("C", ParagraphType.REPLY_TO),
        )).build()

        val post2 = KPostBuilder().setContent(listOf(
            Paragraph("A", ParagraphType.REPLY_TO),
            Paragraph("B", ParagraphType.REPLY_TO),
        )).build()

        val post3 = KPostBuilder().setContent(listOf(
            Paragraph("A", ParagraphType.REPLY_TO),
        )).build()

        assertEquals(2, listOf(post1, post2, post3).replyFor("B").size)
    }
}

