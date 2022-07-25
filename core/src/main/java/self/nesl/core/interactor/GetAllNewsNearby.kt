package self.nesl.core.interactor

import self.nesl.core.data.NewsRepository
import self.nesl.core.domain.News

class GetAllNewsNearby(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(currentLocation: Float, page: Int): List<News> {
        return newsRepository.getAllNewsNearby(currentLocation, 10f, page, 10)
    }
}