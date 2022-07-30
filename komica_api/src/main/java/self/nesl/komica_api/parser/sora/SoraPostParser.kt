package self.nesl.komica_api.parser.sora

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
import self.nesl.komica_api.withProtocol
import java.util.*

/**
 * 可以解析以下 komica.org 的 Post
 *
 *  - [綜合,男性角色,短片2,寫真],
 *  - [新番捏他,新番實況,漫畫,動畫,萌,車],
 *  - [四格],
 *  - [女性角色,歡樂惡搞,GIF,Vtuber],
 *  - [蘿蔔,鋼普拉,影視,特攝,軍武,中性角色,遊戲速報,飲食,小說,遊戲王,奇幻/科幻,電腦/消費電子,塗鴉王國,新聞,布袋戲,紙牌,網路遊戲]
 *
 * @param url https://sora.komica.org/00/pixmicat.php?res=K2345678
 * @param source html
 */
class SoraPostParser(
    private val urlParser: UrlParser,
    private val postHeadParser: PostHeadParser,
): Parser<KPost> {
    private val builder = KPostBuilder()

    override fun parse(source: Element, url: String): KPost {
        val httpUrl = HttpUrl.parse(url)!!
        setDetail(source, httpUrl)
        setContent(source)
        setPicture(source)
        builder.setUrl(url)
        builder.setPostId(urlParser.parsePostId(httpUrl)!!)
        return builder.build()
    }

    private fun setDetail(source: Element, httpUrl: HttpUrl) {
        builder.setTitle(postHeadParser.parseTitle(source, httpUrl) ?: "")
            .setPoster("")
            .setCreatedAt(postHeadParser.parseCreatedAt(source, httpUrl) ?: 0L)
    }

    private fun setContent(source: Element) {
        val list: MutableList<Paragraph> = ArrayList<Paragraph>()
        val parent = source.selectFirst(".quote")
        for (child in parent.childNodes()) {
            if (child is TextNode) {
                list.add(Paragraph(child.text(), ParagraphType.TEXT))
            }
            if (child is Element) {
                if (child.`is`("span.resquote")) {
                    val qlink = child.selectFirst("a.qlink")
                    if (qlink != null) {
                        val replyTo = qlink.text().replace(">".toRegex(), "")
                        list.add(Paragraph(replyTo, ParagraphType.REPLY_TO))
                    } else {
                        val quote = child.ownText().replace(">".toRegex(), "")
                        list.add(Paragraph(quote, ParagraphType.QUOTE))
                    }
                }
                if (child.`is`("a[href^=\"http://\"], a[href^=\"https://\"]")) {
                    list.add(Paragraph(child.ownText(), ParagraphType.LINK))
                }
            }
        }
        builder.setContent(list)
    }

    private fun setPicture(source: Element) {
        source.selectFirst("img")?.let { thumbImg ->
            val originalUrl = thumbImg.parent().attr("href")
            builder.addContent(
                Paragraph(
                    originalUrl.withProtocol(),
                    ParagraphType.IMAGE
                )
            )
        }
    }
}
