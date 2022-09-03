package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import self.nesl.gamer_api.model.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PostParser(
    private val urlParser: UrlParser,
): Parser<GPost> {
    private val builder = GPostBuilder()

    override fun parse(source: Element, url: String): GPost {
        setTitle(source)
        setCreatedAt(source)
        setPosterName(source)
        setPosterId(source)
        setLike(source)
        setUnlike(source)
        setContent(source)
        builder.setUrl(url)
        builder.setPostId(urlParser.parsePostId(HttpUrl.parse(url)!!)!!)
        return builder.build()
    }

    private fun setTitle(source: Element) {
        val text = source.selectFirst("div.c-post__header h1.c-post__header__title").text()
        if (text.isNullOrBlank().not())
            builder.setTitle(text)
    }

    private fun setCreatedAt(source: Element) {
        val element = source.selectFirst("div.c-post__header__info a.edittime.tippy-post-info")
        val string = element.attr("data-mtime")
        if (string.isNullOrBlank().not())
            builder.setCreatedAt(string.toTimestamp())
    }

    private fun setPosterName(source: Element) {
        val text = source.selectFirst("a.username").text()
        if (text.isNullOrBlank().not())
            builder.setPosterName(text)
    }

    private fun setPosterId(source: Element) {
        val text = source.selectFirst("a.userid").text()
        if (text.isNullOrBlank().not())
            builder.setPosterId(text)
    }

    private fun setContent(source: Element) {
        val list: MutableList<GParagraph> = ArrayList<GParagraph>()
        val parent = source.selectFirst("div.c-article__content")
        for (child in parent.childNodes().flatDiv()) {
            if (child is TextNode) {
                list.add(GParagraph(child.text(), GParagraphType.TEXT))
            }
            if (child is Element) {
                if (child.`is`("a.photoswipe-image[href^=\"https://truth.bahamut.com.tw/\"]")) {
                    list.add(GParagraph(child.attr("href"), GParagraphType.IMAGE))
                } else if (child.`is`("a[href^=\"http://\"], a[href^=\"https://\"]")) {
                    list.add(GParagraph(child.ownText(), GParagraphType.LINK))
                }
            }
        }
        builder.setContent(list)
    }

    private fun setLike(source: Element) {
        val string = source.selectFirst("div.gp a.count.tippy-gpbp-list").ownText()
        if (string.isNullOrBlank().not())
            builder.setLike(string.toInt())
    }

    private fun setUnlike(source: Element) {
        val string = source.selectFirst("div.bp a.count.tippy-gpbp-list").ownText()
        if (string.isNullOrBlank().not())
            builder.setUnlike(string.toInt())
    }

    private fun List<Node>.flatDiv(): List<Node> {
        return this.flatMap {
            if (it is Element && it.`is`("div")){
                it.childNodes().flatDiv()
            } else if (it is TextNode) {
                listOf(it, Element("br"))
            } else {
                listOf(it)
            }
        }
    }

    private fun String.toTimestamp(): Long {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            formatter.parse(this).time
        } catch (ignored: ParseException) {
            0L
        }
    }
}