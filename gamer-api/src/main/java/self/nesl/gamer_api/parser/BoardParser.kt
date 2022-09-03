package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost

class BoardParser: Parser<List<GNews>> {
    override fun parse(source: Element, url: String): List<GNews> {
        val newsList = source.select("tr.b-list__row.b-list-item.b-imglist-item:not(.b-list__row--sticky)")
        return newsList.map {
            val title = it.selectFirst("p.b-list__main__title").text()
            val preview = it.selectFirst("p.b-list__brief").text()
            val href = it.selectFirst("a[href^=\"C.php?bsn=\"]").attr("href")
            val threadUrl = "${HttpUrl.parse(url)?.host()}/$href"
            GNews(
                title = title,
                preview = preview,
                url = threadUrl,
            )
        }
    }
}