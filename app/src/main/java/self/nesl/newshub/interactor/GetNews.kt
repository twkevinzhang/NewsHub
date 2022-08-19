package self.nesl.newshub.interactor

import self.nesl.newshub.data.NewsRepository
import javax.inject.Singleton

@Singleton
class GetNews(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(newsId: String) = newsRepository.getNews(newsId)
}