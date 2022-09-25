package self.nesl.newshub.interactor

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.flow.*
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.TopNews
import self.nesl.hub_server.data.news_head.Topic
import self.nesl.hub_server.interactor.TopNewsUseCase
import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllTopNews @Inject constructor(
    private val topNewsUseCase: TopNewsUseCase,
) {
    suspend fun invoke(topicNavItems: TopicNavItems) {
        topNewsUseCase.clearAllTopNews(topicNavItems.toTopic())
    }
}