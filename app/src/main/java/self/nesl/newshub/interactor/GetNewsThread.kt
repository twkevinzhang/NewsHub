package self.nesl.newshub.interactor

import self.nesl.hub_server.data.thread.Thread
import self.nesl.hub_server.interactor.ThreadUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetThread @Inject constructor(
    private val threadUseCase: ThreadUseCase,
) {
    suspend fun invoke(url: String): Thread {
        return threadUseCase.getThread(url)
    }

    suspend fun invoke(url: String, rePost: String): Thread {
        return threadUseCase.getRePostThread(url, rePost)
    }
}