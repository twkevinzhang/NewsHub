package self.nesl.gamer_api.interactor

import self.nesl.gamer_api.parser.UrlParser
import self.nesl.gamer_api.parser.UrlParserImpl

class GetUrlParser {
    fun invoke(): UrlParser {
        return UrlParserImpl()
    }
}