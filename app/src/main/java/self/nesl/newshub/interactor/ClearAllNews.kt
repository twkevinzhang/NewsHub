package self.nesl.newshub.interactor

import self.nesl.hub_server.interactor.NewsUseCase
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllNews @Inject constructor(
    private val newsUseCase: NewsUseCase,
) {
    suspend fun invoke(topicNavItems: TopicNavItems) {
        newsUseCase.clearAllNews(topicNavItems.toTopic())
    }
}