package dev.zlong.hub_server.data.database

import android.util.Log
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import dev.zlong.gamer_api.GamerApi
import dev.zlong.hub_server.data.Host
import dev.zlong.hub_server.data.board.Board
import dev.zlong.hub_server.data.board.toBoard
import dev.zlong.hub_server.di.DataScope
import dev.zlong.hub_server.di.TransactionProvider
import dev.zlong.komica_api.KomicaApi
import javax.inject.Inject
import javax.inject.Provider

class PrePopulateCallBack @Inject constructor(
    private val database: Provider<AppDatabase>,
    private val gamerApi: GamerApi,
    private val komicaApi: KomicaApi,
    @DataScope private val dataScope: CoroutineScope
): RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val dao = database.get().boardDao()
        dataScope.launch {
            try {
                val remote = listOf(
                    async { komicaApi.getAllBoard().map { it.toBoard() } },
                    async { gamerApi.getAllBoard().map { it.toBoard() } }
                ).awaitAll().flatten()
                database.get().withTransaction {
                    dao.upsertAll(remote)
                }
            } catch (e: Exception) {
                Log.e("PrePopulateCallBack", e.stackTraceToString())
            }

            dao.upsertAll(listOf(
                Board("https://gaia.komica.org/00", "綜合", Host.KOMICA, listOf("Square")),
                Board("https://luna.komica.org/23", "GIF", Host.KOMICA, listOf("Square")),
                Board("https://forum.gamer.com.tw/B.php?bsn=60076", "場外休息區", Host.GAMER, listOf("Square")),
            ))
        }
    }
}