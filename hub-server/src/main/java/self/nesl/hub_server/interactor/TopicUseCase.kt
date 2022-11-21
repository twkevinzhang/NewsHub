package self.nesl.hub_server.interactor

import okhttp3.HttpUrl.Companion.toHttpUrl
import self.nesl.hub_server.data.*
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.board.BoardRepository
import self.nesl.hub_server.data.board.toBoard
import self.nesl.hub_server.data.topic.Topic
import self.nesl.hub_server.data.topic.TopicRepository
import self.nesl.komica_api.model.boards
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