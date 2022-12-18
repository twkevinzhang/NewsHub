package dev.zlong.komica_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import dev.zlong.komica_api.model.boards

class GetAllBoard {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        boards()
    }
}