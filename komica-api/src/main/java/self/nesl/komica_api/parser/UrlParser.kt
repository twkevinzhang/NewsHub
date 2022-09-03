package self.nesl.komica_api.parser

import okhttp3.HttpUrl

interface UrlParser {
    fun parsePostId(url: HttpUrl): String?
    fun parseBoardId(url: HttpUrl): String?
}