package self.nesl.komica_api

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.nodes.Element
import self.nesl.komica_api.model.KPost
import self.nesl.komica_api.model.boards
import self.nesl.komica_api.model.replyTo
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

fun String.withProtocol(): String {
    return if (this.startsWith("//")) {
        "http:$this"
    } else {
        this
    }
}

fun String.withProtocol(base: String): String {
    return if (this.startsWith("./") || this.startsWith("/")) {
        if (base.endsWith("/") && this.startsWith("/"))
            base + this.substring(1)
        else if (base.endsWith("/") || this.startsWith("/"))
            base + this
        else
            "$base/$this"
    } else {
        this.withProtocol()
    }
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

fun String.toTimestamp(): Long {
    for (format in listOf(
        "yy/MM/dd(EEE) HH:mm:ss",
        "yy/MM/dd(EEE)HH:mm:ss",
        "yy/MM/dd(EEE)HH:mm",
        "yy/MM/dd HH:mm:ss" // mymoe
    )) {
        try {
            val formatter = SimpleDateFormat(format, Locale.ENGLISH)
            formatter.set2DigitYearStart(Date(946684800))
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

fun List<KPost>.replyFor(threadId: String?): List<KPost> {
    return if(threadId == null)
        this.filter { it.replyTo().isEmpty() }
    else
        this.filter { it.replyTo().contains(threadId) }
}

fun String.isKomica() =
    boards().map { it.url }.contains(this.toHttpUrlOrNull()!!.host)

fun String.toKomicaBoard() =
    boards().first { this.contains(it.url) }