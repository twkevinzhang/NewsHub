package dev.zlong.newshub.interactor

import dev.zlong.hub_server.data.topic.Topic
import dev.zlong.hub_server.interactor.TopicUseCase
import dev.zlong.newshub.R
import dev.zlong.newshub.ui.navigation.NavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicInteractor @Inject constructor(
    private val topicUseCase: TopicUseCase,
) {
    suspend fun get(topicId: String) =
        topicUseCase.get(topicId)

    fun getAll() =
        topicUseCase.getAll()
}

fun Topic.toNavItem(): NavItems {
    val newIcon = if (icon == 0) R.drawable.ic_outline_globe_24 else icon
    return NavItems(
        title = name,
        route = "topic/${id}",
        icon = newIcon,
    )
}