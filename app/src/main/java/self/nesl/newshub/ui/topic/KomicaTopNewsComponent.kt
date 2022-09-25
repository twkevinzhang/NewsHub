package self.nesl.newshub.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_head.komica.mockKomicaTopNews
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.news.CardHeadHostBlock
import self.nesl.newshub.ui.news.CardHeadPosterBlock
import self.nesl.newshub.ui.news.CardHeadRepliesBlock
import self.nesl.newshub.ui.news.CardHeadTimeBlock
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun KomicaTopNewsCard(
    topNews: KomicaTopNews,
) {
    Surface(
        tonalElevation = dimensionResource(id = R.dimen.space_2),
    ) {
        KomicaTopNewsCardContent(topNews)
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaTopNewsCard(
    topNews: KomicaTopNews,
    onClick: () -> Unit,
) {
    Surface(
        tonalElevation = dimensionResource(id = R.dimen.space_2),
        onClick = onClick,
    ) {
        KomicaTopNewsCardContent(topNews)
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Composable
private fun KomicaTopNewsCardContent(topNews: KomicaTopNews) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                CardHeadPosterBlock(topNews.poster)
                CardHeadTimeBlock(topNews.createdAt)
                CardHeadHostBlock(topNews)
            }
            Row {
                CardHeadRepliesBlock(topNews.replies)
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
        if (topNews.title.isNullOrEmpty().not()) {
            KomicaNewsCardTitle(topNews.title!!)
        }
        ParagraphBlock(topNews.content, 100)
    }
}

@Preview
@Composable
fun PreviewKomicaNewsCard() {
    PreviewTheme {
        KomicaTopNewsCard(mockKomicaTopNews())
    }
}

@Composable
fun KomicaNewsCardTitle(text: String) {
    when (text) {
        "無題" ->
            Text(
                text = text,
                style = NewshubTheme.typography.titleMedium.copy(),
                color = LocalContentColor.current.copy(alpha = AppDisabledAlpha),
            )
        else ->
            Text(
                text = text,
                style = NewshubTheme.typography.titleMedium,
            )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}