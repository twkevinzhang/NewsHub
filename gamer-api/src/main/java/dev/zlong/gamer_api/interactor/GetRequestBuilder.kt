package dev.zlong.gamer_api.interactor

import dev.zlong.gamer_api.parser.UrlParser
import dev.zlong.gamer_api.parser.UrlParserImpl
import dev.zlong.gamer_api.request.RequestBuilder
import dev.zlong.gamer_api.request.RequestBuilderImpl

class GetRequestBuilder {
    fun invoke(): RequestBuilder {
        return RequestBuilderImpl()
    }
}