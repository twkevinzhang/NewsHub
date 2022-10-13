package self.nesl.newshub.interactor

import self.nesl.newshub.R
import self.nesl.newshub.ui.navigation.NavItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopic @Inject constructor(
) {
    suspend fun invoke(id: String): Topic {
        return when (id) {
            "Square" -> Square()
            else -> throw NotImplementedError()
        }
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