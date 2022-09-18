package self.nesl.hub_server

import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHead
import self.nesl.hub_server.data.news_thread.komica.KomicaNewsThread
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.model.KPost

fun KPost.toKomicaNewsHead(page: Int) =
    KomicaNewsHead(
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
    )

fun Pair<KPost, List<KPost>>.toKomicaNewsThread(url: String) =
    KomicaNewsThread(
        url = url,
        head = first.toKomicaNewsHead(0),
        comments = second.map { it.toKomicaNewsHead(0) },
    )