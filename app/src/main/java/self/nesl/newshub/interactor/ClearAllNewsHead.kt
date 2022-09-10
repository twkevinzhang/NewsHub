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
class ClearAllNewsHead @Inject constructor(
    private val newsHeadUseCase: NewsHeadUseCase,
) {
    suspend fun invoke(topicNavItems: TopicNavItems) {
        newsHeadUseCase.clearAllNewsHead(topicNavItems.toTopic())
    }
}