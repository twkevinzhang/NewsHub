package self.nesl.newshub.data.news

import self.nesl.newshub.ui.navigation.TopicNavItems


interface NewsLoadMediatorBuilder {
    fun topic(topicNavItems: TopicNavItems): NewsLoadMediatorBuilder

    fun build(): NewsLoadMediator
}
