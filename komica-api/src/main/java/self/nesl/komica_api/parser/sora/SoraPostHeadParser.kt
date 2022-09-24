package self.nesl.komica_api.parser.sora

import okhttp3.HttpUrl
import org.jsoup.nodes.Element
import self.nesl.komica_api.parser.PostHeadParser
import self.nesl.komica_api.replaceChiWeekday
import self.nesl.komica_api.toMillTimestamp

class SoraPostHeadParser: PostHeadParser {
    override fun parseTitle(source: Element, url: HttpUrl): String? {
        val titleE = source.selectFirst("span.title")
        return titleE?.text()
    }

    override fun parseCreatedAt(source: Element, url: HttpUrl): Long {
        var timeE = source.selectFirst("span.now")
        return if (timeE != null) {
            val post_detail = timeE.text().split(" ID:")
            post_detail[0].trim().replaceChiWeekday().toMillTimestamp()
        } else {
            // is 2cat.komica.org
            0
        }
    }

    override fun parsePoster(source: Element, url: HttpUrl): String? {
        // FIXME: wait for JavaScript render
        val head = source.selectFirst("div.post-head")
//            String[] post_detail = head.selectFirst("span.now").text().split(" ID:");
//            return post_detail[1];
        return null
    }
}