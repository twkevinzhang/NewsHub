package self.nesl.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.model.GBoard

class GetBoard {
    suspend fun invoke(url: String) = withContext(Dispatchers.IO) {
        GetAllBoard().invoke().first { it.url == url }
    }
}