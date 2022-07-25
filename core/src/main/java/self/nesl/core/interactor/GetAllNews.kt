package self.nesl.core.interactor

import self.nesl.core.data.NewsRepository
import self.nesl.core.domain.News

class GetAllNews(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(page: Int): List<News> {
        return newsRepository.getAllNews(page, 10)
    }
}