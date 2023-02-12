package dev.zlong.komica_api

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.nodes.Element
import dev.zlong.komica_api.model.boards
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 如果找不到thread標籤，就是2cat.komica.org，要用 [installThreadTag] 改成標準綜合版樣式
 */
fun Element.installThreadTag(): Element {
    if (this.selectFirst("div.thread") != null) return this

    //將thread加入threads中，變成標準綜合版樣式
    var thread = this.appendElement("div").addClass("thread")
    for (div in this.children()) {
        thread.appendChild(div)
        if (div.tagName() == "hr") {
            this.appendChild(thread)
            thread = this.appendElement("div").addClass("thread")
        }
    }
    return this
}

fun String.withHttps(): String {
    return if (this.startsWith("https://")) {
        this
    } else if (this.startsWith("//")) {
        "https:$this"
    } else if (this.startsWith("/")) {
        throw ParseException("The string should not start with /")
    } else {
        "https://$this"
    }
}

fun String.withHttps(base: String): String {
    val startsWithSingleSlash = this.startsWith("/") && !this.startsWith("//")
    return if (!this.startsWith("./") && !startsWithSingleSlash) {
        this.withHttps()
    } else {
        val url = if (base.endsWith("/") && startsWithSingleSlash)
            base + this.substring(1)
        else if (base.endsWith("/") || startsWithSingleSlash)
            base + this
        else
            "$base/$this"
        return url.withHttps()
    }
}

fun String.withHttp(): String {
    return if (this.startsWith("http://")) {
        this
    } else if (this.startsWith("//")) {
        "http:$this"
    } else if (this.startsWith("/")) {
        throw ParseException("The string should not start with /")
    } else {
        "http://$this"
    }
}

fun String.toFolder(): String {
    val url = this.toHttpUrl()
    val pathSegments = url.pathSegments.dropLast(1)
    val hostWithHttps = if (url.isHttps) url.host.withHttps() else url.host.withHttp()
    return hostWithHttps + pathSegments.joinToString("/", prefix = "/")
}

fun String.replaceJpnWeekday(): String {
    val dict = mapOf(
        "月" to "Mon",
        "火" to "Tue",
        "水" to "Wed",
        "木" to "Thu",
        "金" to "Fri",
        "土" to "Sat",
        "日" to "Sun",
    )
    var s = this
    for ((k, v) in dict) {
        s = s.replace(k, v)
    }
    return s
}

fun String.replaceChiWeekday(): String {
    val dict = mapOf(
        "一" to "Mon",
        "二" to "Tue",
        "三" to "Wed",
        "四" to "Thu",
        "五" to "Fri",
        "六" to "Sat",
        "日" to "Sun",
    )
    var s = this
    for ((k, v) in dict) {
        s = s.replace(k, v)
    }
    return s
}

fun String.toMillTimestamp(): Long {
    for (format in listOf(
        "yy/MM/dd(EEE) HH:mm:ss",
        "yy/MM/dd(EEE)HH:mm:ss",
        "yy/MM/dd(EEE)HH:mm",
        "yy/MM/dd HH:mm:ss" // mymoe
    )) {
        try {
            val formatter = SimpleDateFormat(format, Locale.ENGLISH)
            formatter.set2DigitYearStart(Date(946684800)) // 從 2000 年開始
            return formatter.parse(this).time
        } catch (ignored: ParseException) {
        }
    }
    for (format in listOf(
        "yyyy/MM/dd(EEE) HH:mm:ss.SSS",
        "yyyy/MM/dd(EEE)",
    )) {
        try {
            return SimpleDateFormat(format, Locale.ENGLISH).parse(this).time
        } catch (ignored: ParseException) {
        }
    }
    return 0L
}

fun HttpUrl.toKBoard() =
    boards().first { toString().contains(it.url) }

fun HttpUrl.Builder.setFilename(nameWithExtension: String?): HttpUrl.Builder {
    val lastIndex = build().pathSegments.lastIndex
    if (nameWithExtension != null) {
        setPathSegment(lastIndex, nameWithExtension)
    } else {
        removePathSegment(lastIndex)
    }
    return this
}

fun HttpUrl.Builder.addFilename(name: String, extension: String): HttpUrl.Builder {
    addPathSegment("$name.$extension")
    return this
}

fun HttpUrl.Builder.removeFilename(extension: String): HttpUrl.Builder {
    val pathSegments = build().pathSegments
    val last = pathSegments.last()
    if (last.endsWith(".$extension")) {
        removePathSegment(pathSegments.lastIndex)
    }
    return this
}

fun HttpUrl.isFile(name: String, extension: String): Boolean {
    if (!isFile()) return false
    val pathSegments = pathSegments
    val last = pathSegments.last()
    return last == "$name.$extension"
}

fun HttpUrl.isFile(): Boolean {
    val pathSegments = pathSegments
    val last = pathSegments.last()
    return last.contains(".")
}

fun Int?.isZeroOrNull() = this == 0 || this == null

fun Element.toResponseBody(): ResponseBody {
    return this.toString().toResponseBody()
}