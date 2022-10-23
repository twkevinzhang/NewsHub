package self.nesl.gamer_api.parser

import okhttp3.HttpUrl

interface UrlParser {
    fun parsePostId(url: HttpUrl): String?
    fun parseBoardId(url: HttpUrl): String?
    fun parseThreadId(url: HttpUrl): String?
    fun parsePage(url: HttpUrl): Int
    fun parseThreadUrl(url: HttpUrl): String
}