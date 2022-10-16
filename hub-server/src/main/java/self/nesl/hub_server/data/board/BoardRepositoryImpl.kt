package self.nesl.hub_server.data.board

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import self.nesl.gamer_api.GamerApi
import self.nesl.newshub.di.IoDispatcher
import self.nesl.hub_server.di.TransactionProvider
import self.nesl.komica_api.KomicaApi
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val dao: BoardDao,
    private val gamerApi: GamerApi,
    private val komicaApi: KomicaApi,
    private val transactionProvider: TransactionProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): BoardRepository {

    override suspend fun getAllBoards(): List<Board> = withContext(ioDispatcher) {
        val boards = dao.readAll()
        if (boards.isNotEmpty()) {
            boards
        } else {
            try {
                val remote = gamerApi.getAllBoard().map { it.toBoard() }
                    .plus(komicaApi.getAllBoard().map { it.toBoard() })
                transactionProvider.invoke {
                    dao.upsertAll(remote)
                }
                remote
            } catch (e: Exception) {
                Log.e("BoardRepo", e.stackTraceToString())
                emptyList()
            }
        }
    }

    override suspend fun getBoard(url: String): Board {
        val boards = dao.readAll()
        if (boards.isEmpty()) getAllBoards()
        return dao.read(url)!!
    }


    override suspend fun clearAllNews() = withContext(ioDispatcher) {
        dao.clearAll()
    }
}