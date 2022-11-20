package self.nesl.gamer_api.interactor

import self.nesl.gamer_api.parser.UrlParser
import self.nesl.gamer_api.parser.UrlParserImpl
import self.nesl.gamer_api.request.RequestBuilder
import self.nesl.gamer_api.request.RequestBuilderImpl

class GetRequestBuilder {
    fun invoke(): RequestBuilder {
        return RequestBuilderImpl()
    }
}