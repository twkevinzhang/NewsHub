package self.nesl.newshub

import self.nesl.newshub.interactor.GetAllNews
import self.nesl.newshub.interactor.GetAllNewsNearby
import self.nesl.newshub.interactor.GetNews
import javax.inject.Singleton

@Singleton
data class Interactors(
    val getAllNewsNearby: GetAllNewsNearby,
    val getAllNews: GetAllNews,
    val getNews: GetNews,
)
