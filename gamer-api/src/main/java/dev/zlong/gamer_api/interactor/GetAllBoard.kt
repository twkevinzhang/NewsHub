package dev.zlong.gamer_api.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import dev.zlong.gamer_api.model.GBoard

class GetAllBoard {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        listOf(GBoard("場外休息區", "https://forum.gamer.com.tw/B.php?bsn=60076"))
    }
}