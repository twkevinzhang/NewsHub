package self.nesl.newshub.di

import androidx.room.withTransaction
import self.nesl.newshub.data.AppDatabase

class TransactionProvider(
    private val database: AppDatabase
) {
    suspend operator fun <R> invoke(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}