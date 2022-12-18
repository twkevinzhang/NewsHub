package dev.zlong.gamer_api.interactor

import dev.zlong.gamer_api.parser.UrlParser
import dev.zlong.gamer_api.parser.UrlParserImpl

class GetUrlParser {
    fun invoke(): UrlParser {
        return UrlParserImpl()
    }
}