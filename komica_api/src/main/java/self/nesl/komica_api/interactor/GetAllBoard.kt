package self.nesl.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import self.nesl.komica_api.model.boards

class GetAllBoard {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        boards()
    }
}