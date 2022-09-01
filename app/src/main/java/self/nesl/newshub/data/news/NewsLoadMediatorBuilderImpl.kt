package self.nesl.newshub.data.news

import self.nesl.newshub.ui.navigation.TopicNavItems
import javax.inject.Inject


class NewsLoadMediatorBuilderImpl @Inject constructor(
    private val newsKeysDao: NewsKeysDao,
    private val komicaNewsLoadMediator: KomicaNewsLoadMediator,
): NewsLoadMediatorBuilder {
    private var topicNavItems: TopicNavItems? = null

    override fun topic(topicNavItems: TopicNavItems): NewsLoadMediatorBuilder {
        this.topicNavItems = topicNavItems
        return this
    }

    override fun build(): NewsLoadMediator {
        return NewsLoadMediator(
            this.topicNavItems!!,
            newsKeysDao,
            komicaNewsLoadMediator,
        )
    }
}