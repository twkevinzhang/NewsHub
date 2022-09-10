package self.nesl.newshub.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.komica.mockKomicaNewsHead
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme


@Composable
fun KomicaNewsHeadCard(newsHead: NewsHead) {
    Surface(
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    CardHeadPosterBlock(newsHead.poster)
                    CardHeadTimeBlock(newsHead.createdAt)
                    CardHeadHostBlock(newsHead)
                }
                Row {
                    CardHeadRepliesBlock(newsHead.replies)
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            KomicaNewsCardTitle(newsHead.title)
            ParagraphBlock(newsHead.content, 100)
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewKomicaNewsCard() {
    PreviewTheme {
        KomicaNewsHeadCard(mockKomicaNewsHead())
    }
}

@Composable
fun KomicaNewsCardTitle(text: String?) {
    when (text) {
        null, "無題" ->
        {}
        else ->
            Text(
                text = text,
                style = NewshubTheme.typography.h6,
            )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}