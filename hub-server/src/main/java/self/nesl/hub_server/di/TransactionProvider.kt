package self.nesl.hub_server.di

import androidx.room.withTransaction
import self.nesl.hub_server.data.database.AppDatabase

class TransactionProvider(
    private val database: AppDatabase
) {
    suspend operator fun <R> invoke(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}