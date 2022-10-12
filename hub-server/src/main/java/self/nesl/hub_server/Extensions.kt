package self.nesl.hub_server

import self.nesl.hub_server.data.post.Host
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.thread.komica.KomicaThread
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.model.KPost

fun KPost.toKomicaPost(page: Int) =
    KomicaPost(
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

fun Pair<KPost, List<KPost>>.toKomicaThread(url: String) =
    KomicaThread(
        url = url,
        post = first.toKomicaPost(0),
        rePosts = second.map { it.toKomicaPost(0) },
    )

fun String.trySubstring(range: IntRange): String {
    return if (length - 1 < (range.last - range.first)) {
        this
    } else {
        substring(range)
    }
}