package self.nesl.komica_api.model

class KParagraph(
    val content: String,
    val type: KParagraphType
)

enum class KParagraphType {
    QUOTE, REPLY_TO, TEXT, IMAGE, LINK
}
