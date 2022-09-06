package self.nesl.newshub.interactor

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.flow.*
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.Topic
import self.nesl.hub_server.interactor.NewsHeadUseCase
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllNewsHead @Inject constructor(
    private val newsHeadUseCase: NewsHeadUseCase,
) {
    operator fun invoke(topicNavItems: TopicNavItems, hosts: List<Host>): Flow<PagingData<NewsHead>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {
                NewsHeadPagingSource(newsHeadUseCase, topicNavItems.toTopic(), hosts.toSet())
            },
        ).flow
    }

    private fun TopicNavItems.toTopic() =
        when (this) {
            TopicNavItems.Square -> Topic.Square
            TopicNavItems.Movie -> Topic.Movie
        }

    private class NewsHeadPagingSource(
        val newsHeadUseCase: NewsHeadUseCase,
        val topic: Topic,
        val hosts: Set<Host>,
    ) : PagingSource<Int, NewsHead>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsHead> {
            if (hosts.isEmpty()) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
            val first = params.key == null
            val prev = if (first) null else params.key
            val next = if (first) 1 else params.key!! + 1
            val nextPageParameter = if (first) {
                hosts.toMap(1)
            } else {
                hosts.toMap(params.key!! + 1)
            }
            Log.d("NewsHeadPagingSource", "is first? ${first}, prev: ${prev}, next: ${next}, nextPageParameter: ${nextPageParameter}")

            val response = newsHeadUseCase.getAllNewsHead(topic, nextPageParameter)
            return if (response.isEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = prev,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = response,
                    prevKey = prev,
                    nextKey = next
                )
            }
        }

        override fun getRefreshKey(state: PagingState<Int, NewsHead>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey
            }
        }

        private fun <K, V> Set<K>.toMap(fill: V): Map<K, V> {
            val map = mutableMapOf<K, V>()
            this.forEach {
                map[it] = fill
            }
            return map
        }
    }
}