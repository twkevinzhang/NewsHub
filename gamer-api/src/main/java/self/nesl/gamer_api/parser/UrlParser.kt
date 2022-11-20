package self.nesl.gamer_api.parser

import okhttp3.HttpUrl

interface UrlParser {
    fun parseBoardId(url: HttpUrl): String?
    fun parseThreadId(url: HttpUrl): String?
    fun parseThreadUrl(url: HttpUrl): String
    fun parsePostId(url: HttpUrl): String?
    fun parsePage(url: HttpUrl): Int
    fun hasBoardId(url: HttpUrl): Boolean
    fun hasThreadId(url: HttpUrl): Boolean
    fun hasPostId(url: HttpUrl): Boolean
    fun hasPage(url: HttpUrl): Boolean
}