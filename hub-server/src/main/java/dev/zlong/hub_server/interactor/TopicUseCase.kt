package dev.zlong.hub_server.interactor

import okhttp3.HttpUrl.Companion.toHttpUrl
import dev.zlong.hub_server.data.*
import dev.zlong.hub_server.data.board.Board
import dev.zlong.hub_server.data.board.BoardRepository
import dev.zlong.hub_server.data.board.toBoard
import dev.zlong.hub_server.data.topic.Topic
import dev.zlong.hub_server.data.topic.TopicRepository
import dev.zlong.komica_api.model.boards
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicUseCase @Inject constructor(
    private val topicRepository: TopicRepository,
) {
    suspend fun get(id: String) =
        topicRepository.getTopic(id)

    fun getAll() =
        topicRepository.getAllTopics()
}