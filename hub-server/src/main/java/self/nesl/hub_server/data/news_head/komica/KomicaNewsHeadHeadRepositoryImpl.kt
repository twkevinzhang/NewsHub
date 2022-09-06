package self.nesl.hub_server.data.news_head.komica

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.hub_server.data.news_head.*
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.KomicaApi
import self.nesl.komica_api.model.KBoard
import self.nesl.komica_api.model.KPost
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import javax.inject.Inject

class KomicaNewsHeadHeadRepositoryImpl @Inject constructor(
    private val dao: KomicaNewsHeadDao,
    private val api: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): KomicaNewsHeadHeadRepository {

    override suspend fun getAllNewsHead(topic: Topic, page: Int): List<KomicaNewsHead> = withContext(ioDispatcher) {
        val news = dao.readAll(page)
        if (news.isNotEmpty()) {
            return@withContext news
        } else {
            val remote = api.getAllThreadHead(topic.toKBoard(), page.takeIf { it != 0 }).map { it.toKomicaNews(page) }
            transactionProvider.invoke {
                dao.upsertAll(remote)
            }
            remote
        }
    }

    private fun Topic.toKBoard() =
        when (this) {
            Topic.Square -> KBoard.Sora.綜合
            else -> throw NotImplementedError()
        }

    private fun KPost.toKomicaNews(page: Int) =
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
}