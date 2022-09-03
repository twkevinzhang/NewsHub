package self.nesl.newshub.data.news

import androidx.paging.*
import androidx.paging.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import self.nesl.komica_api.model.KPost
import self.nesl.newshub.di.IoDispatcher
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsLoadMediatorBuilder: NewsLoadMediatorBuilder,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): NewsRepository {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun getAllNews(
        topicNavItems: TopicNavItems,
        excludeHost: List<Host>,
    ) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = newsLoadMediatorBuilder
                .topic(topicNavItems)
                .build(),
            pagingSourceFactory = {
                newsDao.readAll(excludeHost)
            },
        ).flow
}