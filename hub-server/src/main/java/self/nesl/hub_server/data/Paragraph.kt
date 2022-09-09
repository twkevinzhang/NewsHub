package self.nesl.hub_server.data

import self.nesl.komica_api.model.*
import java.lang.IllegalArgumentException

open class Paragraph(
    val type: ParagraphType,
)

enum class ParagraphType {
    QUOTE, REPLY_TO, TEXT, IMAGE, LINK
}

class ImageInfo(
    val thumb: String? = null,
    val raw: String,
): Paragraph(ParagraphType.IMAGE)

class Text(
    val content: String,
): Paragraph(ParagraphType.TEXT)

class Quote(
    val content: String,
): Paragraph(ParagraphType.QUOTE)

class ReplyTo(
    val content: String,
): Paragraph(ParagraphType.REPLY_TO)

class Link(
    val content: String,
): Paragraph(ParagraphType.LINK)

fun KParagraph.toParagraph(): Paragraph {
    return when (this) {
        is KQuote -> Quote(content)
        is KReplyTo -> ReplyTo(content)
        is KText -> Text(content)
        is KImageInfo -> ImageInfo(thumb, raw)
        is KLink -> Link(content)
        else -> {
            throw IllegalArgumentException("Unknown paragraph type")
        }
    }
}