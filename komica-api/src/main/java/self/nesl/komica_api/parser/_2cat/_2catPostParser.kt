package self.nesl.komica_api.parser._2cat

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.KPostBuilder
import self.nesl.komica_api.model.KParagraph
import self.nesl.komica_api.model.KParagraphType
import self.nesl.komica_api.parser.Parser
import self.nesl.komica_api.parser.PostHeadParser
import self.nesl.komica_api.parser.UrlParser
import java.util.*
import java.util.regex.Pattern

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
        val contents = source.selectFirst(".quote").childNodes()
            .filterIsInstance<TextNode>()
            .flatMap {
                resolveLink(it.text()) { link ->
                    if (IMAGE_URL_PATTERN.matcher(link).find()) {
                        KParagraph(link, KParagraphType.IMAGE)
                    } else {
                        KParagraph(link, KParagraphType.LINK)
                    }
                }
            }
        builder.setContent(contents)
    }

    private fun setPicture(source: Element, url: HttpUrl) {
        source.selectFirst("a.imglink[href=#]")?.let { thumbImg ->
            val fileName = source.selectFirst("a.imglink[href=#]").attr("title")
            val newLink = "http://img.2nyan.org/${urlParser.parseBoardId(url)}/src/${fileName}"
            builder.addContent(
                KParagraph(
                    newLink,
                    KParagraphType.IMAGE
                )
            )
        }
    }

    /**
     * 解析文章，裡面可能包含連結
     * @param article 文章
     */
    private fun resolveLink(article: String, callback: (String) -> KParagraph = {
        KParagraph(it, KParagraphType.LINK)
    }): List<KParagraph> {
        val m = WEB_URL_PATTERN.matcher(article)
        val list: MutableList<KParagraph> = ArrayList()
        var index = 0
        while (m.find()) {
            val url = m.group()
            val preParagraph = article.substring(index, m.start())
            list.add(KParagraph(preParagraph, KParagraphType.TEXT))
            list.add(callback(url))
            index = m.end()
        }
        val lastParagraph = article.substring(index)
        list.add(KParagraph(lastParagraph, KParagraphType.TEXT))
        return list
    }

    companion object {
        private val WEB_URL_PATTERN = Pattern.compile("((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+")
        private val IMAGE_URL_PATTERN = Pattern.compile("(http(s?):/)(/[^/]+)+\\.(?:jpg|gif|png|webm)")
    }
}