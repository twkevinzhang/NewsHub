package self.nesl.newshub.interactor

import self.nesl.newshub.data.News
import self.nesl.newshub.data.NewsRepository

class GetAllNews(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(page: Int): List<News> {
        return newsRepository.getAllNews(page, 10)
    }
}