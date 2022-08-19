package self.nesl.newshub.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import self.nesl.newshub.data.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [DataModule.RepositoryBinder::class])
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
    fun provideNewsDataSource(
        database: AppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NewsDataSource = NewsDataSourceImpl(database.newsDao(), ioDispatcher)

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {
        @Binds
        abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
    }
}
