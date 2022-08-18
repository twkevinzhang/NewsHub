package self.nesl.newshub.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import self.nesl.core.data.NewsDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import self.nesl.komica_api.interactor.GetAllThreadHead
import self.nesl.newshub.BuildConfig
import self.nesl.newshub.framework.data.NewsDataSourceImpl

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().run {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
            build()
        }
    }
}
