package self.nesl.newshub.interactor

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.flow.*
import self.nesl.hub_server.data.post.Host
import self.nesl.hub_server.data.post.News
import self.nesl.hub_server.data.post.Topic
import self.nesl.hub_server.interactor.NewsUseCase
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllNews @Inject constructor(
    private val newsUseCase: NewsUseCase,
) {
    fun invoke(topicNavItems: TopicNavItems, hosts: List<Host>): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = {
                NewsPagingSource(newsUseCase, topicNavItems.toTopic(), hosts.toSet())
            },
        ).flow
    }

    private class NewsPagingSource(
        val newsUseCase: NewsUseCase,
        val topic: Topic,
        val hosts: Set<Host>,
    ) : PagingSource<Int, News>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
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
            Log.d("NewsPagingSource", "is first? ${first}, prev: ${prev}, next: ${next}, nextPageParameter: ${nextPageParameter}")

            val response = newsUseCase.getAllNews(topic, nextPageParameter)
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

        override fun getRefreshKey(state: PagingState<Int, News>): Int? {
            return null
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

fun TopicNavItems.toTopic() =
    when (this) {
        TopicNavItems.Square -> Topic.Square
        TopicNavItems.Movie -> Topic.Movie
    }