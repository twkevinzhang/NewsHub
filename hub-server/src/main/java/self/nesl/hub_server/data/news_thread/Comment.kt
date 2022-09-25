package self.nesl.hub_server.data.news_thread

import androidx.room.PrimaryKey
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news_head.TopNews

interface Comment {
    val url: String
    val content: List<Paragraph>
}