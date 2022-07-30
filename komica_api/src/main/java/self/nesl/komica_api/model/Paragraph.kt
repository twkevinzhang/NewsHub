package self.nesl.komica_api.model

class Paragraph(
    val content: String,
    val type: ParagraphType
)

enum class ParagraphType {
    QUOTE, REPLY_TO, TEXT, IMAGE, LINK
}
