package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import self.nesl.komica_api.ParseException
import self.nesl.komica_api.model.*
import self.nesl.komica_api.parser.PostHeadParser
import self.nesl.komica_api.parser.Parser
import self.nesl.komica_api.parser.UrlParser
import self.nesl.komica_api.withHttps
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
        val httpUrl = url.toHttpUrlOrNull()!!
        setDetail(source, httpUrl)
        setContent(source)
        setPicture(source, httpUrl.host)
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
        val list: MutableList<KParagraph> = ArrayList<KParagraph>()
        val parent = source.selectFirst(".quote")
        for (child in parent.childNodes()) {
            if (child is TextNode) {
                list.add(KText(child.text()))
            }
            if (child is Element) {
                if (child.`is`("span.resquote")) {
                    val qlink = child.selectFirst("a.qlink")
                    if (qlink != null) {
                        val replyTo = qlink.text()
                            .replace(">".toRegex(), "") // for sora.komica.org
                            .replace("No.", "") // for 2cat.komica.org
                        list.add(KReplyTo(replyTo))
                    } else {
                        val quote = child.ownText().replace(">".toRegex(), "")
                        list.add(KQuote(quote))
                    }
                }
                if (child.`is`("a[href^=\"http://\"], a[href^=\"https://\"]")) {
                    list.add(KLink(child.ownText()))
                }
            }
        }
        builder.setContent(list)
    }

    private fun setPicture(source: Element, host: String) {
        source.selectFirst("img")?.let { thumbImg ->
            val rawUrl = thumbImg.parent().attr("href")
            val thumbUrl = thumbImg.attr("src")

            try {
                builder.addContent(
                    KImageInfo(thumbUrl.withHttps(), rawUrl.withHttps())
                )
            } catch (e: ParseException) {
                builder.addContent(
                    KImageInfo(thumbUrl.withHttps(host), rawUrl.withHttps(host))
                )
            }
        }
    }
}
