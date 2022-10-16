package self.nesl.gamer_api.model

interface GParagraph

class GImageInfo(
    val thumb: String? = null,
    val raw: String,
): GParagraph

class GText(
    val content: String,
): GParagraph

class GQuote(
    val content: String,
): GParagraph

class GReplyTo(
    val content: String,
): GParagraph

class GLink(
    val content: String,
): GParagraph