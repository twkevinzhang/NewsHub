package self.nesl.newshub.interactor

import self.nesl.newshub.R
import self.nesl.newshub.ui.navigation.NavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllTopic @Inject constructor(
) {
    suspend fun invoke(): List<Topic> {
        return listOf(Square())
    }
}