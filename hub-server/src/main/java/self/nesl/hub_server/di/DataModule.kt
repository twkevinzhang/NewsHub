package self.nesl.hub_server.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import self.nesl.hub_server.data.AppDatabase
import self.nesl.hub_server.data.post.komica.KomicaPostRepository
import self.nesl.hub_server.data.post.komica.KomicaPostRepositoryImpl
import self.nesl.hub_server.data.thread.komica.KomicaThreadRepository
import self.nesl.hub_server.data.thread.komica.KomicaThreadRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [
    DataModule.RepositoryBinder::class,
])
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTransactionProvider(database: AppDatabase) = TransactionProvider(database)

    @Singleton
    @Provides
    fun provideKomicaPostDao(database: AppDatabase) = database.komicaPostDao()

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {

        @Binds
        abstract fun bindNewsRepository(impl: KomicaPostRepositoryImpl): KomicaPostRepository

        @Binds
        abstract fun bindThreadRepository(impl: KomicaThreadRepositoryImpl): KomicaThreadRepository
    }
}
