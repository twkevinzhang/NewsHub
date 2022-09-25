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
import self.nesl.hub_server.data.news_head.komica.KomicaTopNewsRepository
import self.nesl.hub_server.data.news_head.komica.KomicaTopNewsRepositoryImpl
import self.nesl.hub_server.data.news_thread.komica.KomicaNewsThreadRepository
import self.nesl.hub_server.data.news_thread.komica.KomicaNewsThreadRepositoryImpl
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
    fun provideKomicaTopNewsDao(database: AppDatabase) = database.komicaTopNewsDao()

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {

        @Binds
        abstract fun bindTopNewsRepository(impl: KomicaTopNewsRepositoryImpl): KomicaTopNewsRepository

        @Binds
        abstract fun bindNewsThreadRepository(impl: KomicaNewsThreadRepositoryImpl): KomicaNewsThreadRepository
    }
}
