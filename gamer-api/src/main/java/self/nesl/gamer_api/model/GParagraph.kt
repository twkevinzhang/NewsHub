package self.nesl.gamer_api.model

class GParagraph(
    val content: String,
    val type: GParagraphType
) {
    override fun toString(): String {
        return "GParagraph(content = $content, type = $type)"
    }
}

enum class GParagraphType {
    QUOTE, REPLY_TO, TEXT, IMAGE, LINK
}
