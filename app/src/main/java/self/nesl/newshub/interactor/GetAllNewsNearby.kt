package self.nesl.newshub.interactor

import self.nesl.newshub.data.News
import self.nesl.newshub.data.NewsRepository

class GetAllNewsNearby(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(currentLocation: Float, page: Int): List<News> {
        return newsRepository.getAllNewsNearby(currentLocation, 10f, page, 10)
    }
}