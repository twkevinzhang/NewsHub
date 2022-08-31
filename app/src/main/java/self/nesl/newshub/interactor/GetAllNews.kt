package self.nesl.newshub.interactor

import androidx.paging.PagingData
import kotlinx.coroutines.flow.*
import self.nesl.newshub.data.news.Host
import self.nesl.newshub.data.news.News
import self.nesl.newshub.data.news.NewsRepository
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllNews @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(topicNavItems: TopicNavItems): Flow<PagingData<News>> {
        return newsRepository.getAllNews(topicNavItems)
    }
}