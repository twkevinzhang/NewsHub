package self.nesl.newshub.data.news

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import self.nesl.newshub.ui.navigation.TopicNavItems

interface NewsRepository {
    fun getAllNews(topicNavItems: TopicNavItems, excludeHost: List<Host>): Flow<PagingData<News>>
}