package self.nesl.newshub.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_head.komica.mockKomicaTopNews
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaTopNewsCard(
    topNews: KomicaTopNews,
    onLinkClick: (Paragraph.Link) -> Unit,
    onClick: () -> Unit = { },
) {
    Surface(
        tonalElevation = dimensionResource(id = R.dimen.space_2),
        onClick = onClick,
    ) {
        KomicaTopNewsCardContent(
            topNews = topNews,
            onLinkClick = onLinkClick,
            onReplyToClick = { },
            onPreviewReplyTo = { "" },
        )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Composable
fun KomicaCommentCard(
    comment: KomicaTopNews,
    onLinkClick: (Paragraph.Link) -> Unit = { },
    onReplyToClick: (Paragraph.ReplyTo) -> Unit = { },
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String  = { "" },
) {
    Surface(
        tonalElevation = dimensionResource(id = R.dimen.space_2),
    ) {
        KomicaTopNewsCardContent(
            topNews = comment,
            onLinkClick = onLinkClick,
            onReplyToClick = onReplyToClick,
            onPreviewReplyTo = onPreviewReplyTo,
        )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Composable
private fun KomicaTopNewsCardContent(
    topNews: KomicaTopNews,
    onLinkClick: (Paragraph.Link) -> Unit,
    onReplyToClick: (Paragraph.ReplyTo) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
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
        ParagraphBlock(
            topNews.content,
            100,
            onLinkClick,
            onReplyToClick,
            onPreviewReplyTo,
        )
    }
}

@Preview
@Composable
fun PreviewKomicaNewsCard() {
    PreviewTheme {
        KomicaTopNewsCard(
            topNews = mockKomicaTopNews(),
            onLinkClick = { },
            onClick = { },
        )
    }
}

@Composable
private fun KomicaNewsCardTitle(text: String) {
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