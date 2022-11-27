package self.nesl.gamer_api.parser

import okhttp3.Request
import org.jsoup.nodes.Element

interface Parser<T> {
    fun parse(source: Element, req: Request): T
}