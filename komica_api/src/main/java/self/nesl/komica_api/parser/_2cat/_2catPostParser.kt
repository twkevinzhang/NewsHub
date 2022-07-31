package self.nesl.komica_api.parser._2cat

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KPostBuilder
import self.nesl.komica_api.model.Paragraph
import self.nesl.komica_api.model.ParagraphType
import self.nesl.komica_api.parser.PostHeadParser
import self.nesl.komica_api.parser.Parser
import self.nesl.komica_api.parser.UrlParser
import java.util.*
import java.util.regex.Matcher

class _2catPostParser(
    private val urlParser: UrlParser,
    private val postHeadParser: PostHeadParser,
): Parser<KPost> {
    private val builder = KPostBuilder()

    override fun parse(source: Element, url: String): KPost {
        val httpUrl = HttpUrl.parse(url)!!
        setDetail(source, httpUrl)
        setContent(source)
        setPicture(source, httpUrl)
        builder.setUrl(url)
        builder.setPostId(urlParser.parsePostId(httpUrl)!!)
        return builder.build()
    }

    private fun setDetail(source: Element, url: HttpUrl) {
        builder.setTitle(postHeadParser.parseTitle(source, url) ?: "")
            .setPoster("")
            .setCreatedAt(postHeadParser.parseCreatedAt(source, url) ?: 0L)
    }

    private fun setContent(source: Element) {
        val list: MutableList<Paragraph> = ArrayList()
        for (child in source.selectFirst(".quote").childNodes()) {
            if (child is TextNode) {
                val text = child.text()
//                list.addAll(parseLinkedArticle(text))
            }
        }
        builder.setContent(list)
    }

    private fun setPicture(source: Element, url: HttpUrl) {
        source.selectFirst("a.imglink[href=#]")?.let { thumbImg ->
            val fileName = source.selectFirst("a.imglink[href=#]").attr("title")
            val newLink = "http://img.2nyan.org/${urlParser.parseBoardId(url)}/src/${fileName}"
            builder.addContent(
                Paragraph(
                    newLink,
                    ParagraphType.IMAGE
                )
            )
        }
    }

    /**
     * 解析「包含連結的文章」
     * @param linkedArticle 包含連結的文章內容
     */
//    private fun parseLinkedArticle(linkedArticle: String): List<Paragraph>? {
//        val list: MutableList<Paragraph> = ArrayList()
//        val m: Matcher = android.util.Patterns.WEB_URL.matcher(linkedArticle)
//        var index = 0
//        while (m.find()) {
//            val url = m.group()
//            val preParagraph = linkedArticle.substring(index, m.start())
//            list.add(Paragraph(preParagraph, ParagraphType.String))
//            if (_2catPostParser.IMAGE_LINK_PATTERN.matcher(url).find()) {
//                list.add(Paragraph(url, ParagraphType.IMAGE))
//            } else {
//                list.add(Paragraph(url, ParagraphType.LINK))
//            }
//            index = m.end()
//        }
//        val lastParagraph = linkedArticle.substring(index)
//        list.add(Paragraph(lastParagraph, ParagraphType.String))
//        return list
//    }
}