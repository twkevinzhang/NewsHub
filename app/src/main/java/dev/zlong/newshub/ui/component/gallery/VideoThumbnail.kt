package dev.zlong.newshub.ui.component.gallery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dev.zlong.hub_server.data.Paragraph
import dev.zlong.hub_server.data.ParagraphType
import dev.zlong.hub_server.data.post.Post
import dev.zlong.hub_server.data.post.gamer.GamerPost
import dev.zlong.hub_server.data.post.komica.KomicaPost
import dev.zlong.newshub.model.Thumbnail
import dev.zlong.newshub.ui.news.GamerPostCard
import dev.zlong.newshub.ui.news.KomicaPostCard


@Composable
fun VideoThumbnail(
    video: Thumbnail,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        SubcomposeAsyncImage(
            model = video.url,
            contentDescription = null,
            loading = {
                CircularProgressIndicator()
            },
            error = { Text("Error") },
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
        )
        IconButton(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
                .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(0.75F),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview
@Composable
fun PreviewVideoThumbnail(){
    VideoThumbnail(
        Thumbnail(
            "https://i.imgur.com/removed.png",
            "https://i.imgur.com/removed.png",
            ParagraphType.VIDEO,
        )
    ) {}
}