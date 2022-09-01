package self.nesl.newshub.data.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.CoroutineDispatcher
import self.nesl.komica_api.KomicaApi
import self.nesl.newshub.data.AppDatabase
import self.nesl.newshub.di.IoDispatcher
import self.nesl.newshub.di.TransactionProvider
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsKeysDao: NewsKeysDao,
    private val transactionProvider: TransactionProvider,
    private val api: KomicaApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): NewsRepository {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun getAllNews(topicNavItems: TopicNavItems) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = NewsLoadMediator(
                topicNavItems = topicNavItems,
                newsKeysDao = newsKeysDao,
                komicaNewsLoadMediator = KomicaNewsLoadMediator(
                    transactionProvider = transactionProvider,
                    newsDao = newsDao,
                    newsKeysDao = newsKeysDao,
                    api = api,
                ),
            ),
            pagingSourceFactory = {
                newsDao.readAll()
            },
        ).flow
}