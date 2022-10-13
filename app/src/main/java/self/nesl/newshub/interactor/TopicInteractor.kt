package self.nesl.newshub.interactor

import self.nesl.newshub.R
import self.nesl.newshub.ui.navigation.NavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicInteractor @Inject constructor(
) {
    suspend fun get(topicId: String): Topic {
        return when (topicId) {
            "Square" -> Square()
            else -> throw NotImplementedError()
        }
    }

    suspend fun getAll(): List<Topic> {
        return listOf(Square())
    }
}

open class Topic (
    val id: String,
    val name: String,
)

class Square: Topic("Square", "Square")

fun Topic.toNavItem(): NavItems {
    return NavItems(
        title = name,
        route = "topic/${id}",
        icon = R.drawable.ic_outline_globe_24,
    )
}