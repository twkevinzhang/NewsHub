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
import self.nesl.hub_server.data.board.BoardRepository
import self.nesl.hub_server.data.board.BoardRepositoryImpl
import self.nesl.hub_server.data.news.gamer.GamerNewsRepository
import self.nesl.hub_server.data.news.gamer.GamerNewsRepositoryImpl
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

    @Singleton
    @Provides
    fun provideGamerNewsDao(database: AppDatabase) = database.gamerNewsDao()

    @Singleton
    @Provides
    fun provideBoardDao(database: AppDatabase) = database.boardDao()

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryBinder {

        @Binds
        abstract fun bindBoardRepository(impl: BoardRepositoryImpl): BoardRepository

        @Binds
        abstract fun bindKomicaNewsRepository(impl: KomicaPostRepositoryImpl): KomicaPostRepository

        @Binds
        abstract fun bindKomicaThreadRepository(impl: KomicaThreadRepositoryImpl): KomicaThreadRepository

        @Binds
        abstract fun bindGamerNewsRepository(impl: GamerNewsRepositoryImpl): GamerNewsRepository
    }
}
