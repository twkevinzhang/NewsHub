package self.nesl.hub_server.data.thread.komica

import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.post.komica.toKomicaPost
import self.nesl.hub_server.data.thread.Thread
import self.nesl.komica_api.model.KPost

data class KomicaThread (
    override val url: String,
    override val post: Post,
    override val rePosts: List<Post>,
): Thread

fun Pair<KPost, List<KPost>>.toKomicaThread(url: String, boardUrl: String) =
    KomicaThread(
        url = url,
        post = first.toKomicaPost(0, boardUrl),
        rePosts = second.map { it.toKomicaPost(0, boardUrl) },
    )