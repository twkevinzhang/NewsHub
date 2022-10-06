package self.nesl.hub_server

import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_thread.komica.KomicaNewsThread
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.model.KPost

fun KPost.toKomicaTopNews(page: Int) =
    KomicaTopNews(
        host = Host.KOMICA,
        url = url,
        title = title,
        createdAt = createdAt,
        poster = poster,
        visits = visits,
        replies = replies,
        readAt = readAt,
        content = content.map { it.toParagraph() },
        favorite = null,
        page = page,
        id = id,
    )

fun Pair<KPost, List<KPost>>.toKomicaNewsThread(url: String) =
    KomicaNewsThread(
        url = url,
        head = first.toKomicaTopNews(0),
        comments = second.map { it.toKomicaTopNews(0) },
    )

fun String.trySubstring(range: IntRange): String {
    return if (length - 1 < (range.last - range.first)) {
        this
    } else {
        substring(range)
    }
}