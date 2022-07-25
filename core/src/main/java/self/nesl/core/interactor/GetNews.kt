package self.nesl.core.interactor

import self.nesl.core.data.NewsRepository

class GetNews(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(newsId: String) = newsRepository.getNews(newsId)
}