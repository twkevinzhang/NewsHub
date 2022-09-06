package self.nesl.hub_server.data

import self.nesl.komica_api.model.KParagraph
import self.nesl.komica_api.model.KParagraphType

class Paragraph(
    val content: String,
    val type: ParagraphType
)

enum class ParagraphType {
    QUOTE, REPLY_TO, TEXT, IMAGE, LINK
}

fun KParagraph.toParagraph(): Paragraph {
    return when (type) {
        KParagraphType.QUOTE -> Paragraph(content, ParagraphType.QUOTE)
        KParagraphType.REPLY_TO -> Paragraph(content, ParagraphType.REPLY_TO)
        KParagraphType.TEXT -> Paragraph(content, ParagraphType.TEXT)
        KParagraphType.IMAGE -> Paragraph(content, ParagraphType.IMAGE)
        KParagraphType.LINK -> Paragraph(content, ParagraphType.LINK)
    }
}