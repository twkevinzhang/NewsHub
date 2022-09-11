package self.nesl.newshub.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHead
import self.nesl.hub_server.data.news_head.komica.mockKomicaNewsHead
import self.nesl.newshub.R
import self.nesl.newshub.toHumanTime
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme



@Composable
fun CardHeadPosterBlock(poster: String?) {
    CardHeadTextBlock(poster?.takeIf { it.isNotBlank() }.toString())
}

@Composable
fun CardHeadTimeBlock(timestamp: Long?) {
    CardHeadTextBlock(timestamp?.toHumanTime() ?: "time")
}

@Composable
fun CardHeadHostBlock(newsHead: NewsHead) {
    CardHeadTextBlock(
        when (newsHead) {
            is KomicaNewsHead -> "Komica"
            else -> "not support"
        }
    )
}

@Composable
fun CardHeadRepliesBlock(replies: Int?) {
    CardHeadTextBlock(replies.toString())
}

@Composable
fun CardHeadBlock(
    modifier: Modifier = Modifier,
    compose: @Composable () -> Unit = { },
) {
    Box(
        modifier = modifier
            .padding(end = dimensionResource(id = R.dimen.space_4))
    ) {
        compose()
    }
}

@Composable
fun CardHeadTextBlock(
    text: String = "",
    modifier: Modifier = Modifier,

    ) {
    CardHeadBlock(
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = NewshubTheme.typography.bodySmall,
            color = LocalContentColor.current.copy(alpha = 0.4f),
        )
    }
}