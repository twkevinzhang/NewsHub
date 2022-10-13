package self.nesl.newshub.interactor

import self.nesl.hub_server.data.post.Board
import self.nesl.hub_server.interactor.NewsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllNews @Inject constructor(
    private val newsUseCase: NewsUseCase,
) {
    suspend fun invoke(boards: Set<Board>) {
        newsUseCase.clearAllNews(boards)
    }
}