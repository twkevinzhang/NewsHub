package self.nesl.core.domain

data class News(
    val id: String,
    val topic: String,
    val content: String,
    val locationX: Float,
    val locationY: Float,
)
