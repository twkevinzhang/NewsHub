package self.nesl.gamer_api.parser

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.nodes.Element
import self.nesl.gamer_api.model.GBoard
import self.nesl.gamer_api.model.GNews
import self.nesl.gamer_api.model.GPost

class BoardParser(
    private val newsParser: NewsParser,
): Parser<List<GNews>> {
    override fun parse(source: Element, url: String): List<GNews> {
        val newsList = source.select("tr.b-list__row.b-list-item.b-imglist-item:not(.b-list__row--sticky)")
        return newsList.map { newsParser.parse(it, url) }
    }
}