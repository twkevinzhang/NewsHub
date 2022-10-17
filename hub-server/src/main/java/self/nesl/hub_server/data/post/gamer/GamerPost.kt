package self.nesl.hub_server.data.post.gamer

import androidx.room.Entity
import androidx.room.PrimaryKey
import self.nesl.gamer_api.model.GPost
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.toParagraph
import self.nesl.komica_api.model.KPost

data class GamerPost (
    override val url: String,
    override val id: String,
    override val boardUrl: String,
    override val title: String,
    val createdAt: Long?,
    val replies: Int?,
    val readAt: Int?,
    override val content: List<Paragraph>,
    val page: Int,
): Post

fun GPost.toGamerPost(page: Int, boardUrl: String) =
    GamerPost(
        boardUrl = boardUrl,
        url = url,
        title = title,
        createdAt = createdAt,
        replies = replies,
        readAt = readAt,
        content = content.map { it.toParagraph() },
        page = page,
        id = id,
    )
