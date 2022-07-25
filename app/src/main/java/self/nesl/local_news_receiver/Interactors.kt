package self.nesl.local_news_receiver

import self.nesl.core.interactor.GetAllNews
import self.nesl.core.interactor.GetAllNewsNearby
import self.nesl.core.interactor.GetNews

data class Interactors(
    val getAllNewsNearby: GetAllNewsNearby,
    val getAllNews: GetAllNews,
    val getNews: GetNews,
)
