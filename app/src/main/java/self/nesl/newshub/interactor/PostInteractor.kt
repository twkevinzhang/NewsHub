package self.nesl.newshub.interactor

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import self.nesl.hub_server.data.news.News
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.interactor.PostUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostInteractor @Inject constructor(
    private val postUseCase: PostUseCase,
) {
    fun getAll(url: String, rePostId: String? = null): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(postUseCase, url, rePostId) },
        ).flow
    }

    private class PostPagingSource(
        val postUseCase: PostUseCase,
        val url: String,
        val rePostId: String? = null,
    ) : PagingSource<Int, Post>() {
        val isRePostThread = rePostId != null

        /**
         * page: 第一頁：1，第二頁：2，第三頁:3
         */
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
            val first = params.key == null
            val prev = if (first) null else params.key
            val next = if (first) 1 else params.key!! + 1
            val response = if (isRePostThread)
                postUseCase.getRePostThread(url, rePostId!!, next)
            else
                postUseCase.getAll(url, next)

            return if (response.isEmpty() || isRePostThread) {
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

        override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
            return null
        }
    }
}