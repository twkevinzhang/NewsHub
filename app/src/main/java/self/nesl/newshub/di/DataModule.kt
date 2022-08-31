package self.nesl.newshub.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import self.nesl.newshub.data.*
import self.nesl.newshub.data.news.NewsRepository
import self.nesl.newshub.data.news.NewsRepositoryImpl
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
    fun provideNewsDao(database: AppDatabase) = database.newsDao()

    @Singleton
    @Provides
    fun provideNewsKeysDao(database: AppDatabase) = database.newsKeysDao()

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {

        @Binds
        abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
    }
}
