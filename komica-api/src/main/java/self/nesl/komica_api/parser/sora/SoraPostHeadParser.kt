package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.komica_api.parser.PostHeadParser
import self.nesl.komica_api.replaceChiWeekday
import self.nesl.komica_api.replaceJpnWeekday
import self.nesl.komica_api.toTimestamp

class SoraPostHeadParser: PostHeadParser {
    override fun parseTitle(source: Element, url: HttpUrl): String? {
        val head = source.selectFirst("div.post-head")
        val titleE = head.selectFirst("span.title")
        return titleE?.text()
    }

    override fun parseCreatedAt(source: Element, url: HttpUrl): Long {
        val head = source.selectFirst("div.post-head")
        var timeE = head.selectFirst("span.now")
        if (timeE == null) {
            // is 2cat.komica.org
            timeE = head
        }
        val post_detail = timeE.text().split(" ID:")
        return post_detail[0].trim().replaceChiWeekday().toTimestamp()
    }

    override fun parsePoster(source: Element, url: HttpUrl): String? {
        // FIXME: wait for JavaScript render
        val head = source.selectFirst("div.post-head")
//            String[] post_detail = head.selectFirst("span.now").text().split(" ID:");
//            return post_detail[1];
        return null
    }
}