package self.nesl.newshub.data.news

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import self.nesl.newshub.ui.navigation.TopicNavItems
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsLoadMediator (
    private val topicNavItems: TopicNavItems,
    private val newsKeysDao: NewsKeysDao,
    private val komicaNewsLoadMediator: KomicaNewsLoadMediator,
): RemoteMediator<Int, News>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                val currentPage = remoteKeys?.nextKey?.minus(1) ?: 1
                Log.d("NewsLoadMediator", "is REFRESH, currentPage $currentPage")
                currentPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Log.d("NewsLoadMediator", "is PREPEND, prevPage $prevKey, first is ${remoteKeys?.url?.takeLast(10)}")
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Log.d("NewsLoadMediator", "is APPEND, nextPage $nextKey, last is ${remoteKeys?.url?.takeLast(10)}")
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            val komicaIsEnded = komicaNewsLoadMediator.getEndOfPaginationReachedAfterLoad(
                loadType = loadType,
                page = page,
                topicNavItems = topicNavItems,
            )
            MediatorResult.Success(
                endOfPaginationReached = komicaIsEnded
            )
        } catch (exception: IOException) {
            Log.e("NewsLoadMediator", exception.stackTraceToString())
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, News>): NewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { news ->
                newsKeysDao.remoteKeysUrl(news.url)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, News>): NewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { news ->
                newsKeysDao.remoteKeysUrl(news.url)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, News>): NewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                newsKeysDao.remoteKeysUrl(url)
            }
        }
    }

}
