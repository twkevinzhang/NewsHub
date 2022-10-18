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
import self.nesl.hub_server.data.news.gamer.GamerNews
import self.nesl.hub_server.data.news.gamer.mockGamerNews
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamerNewsCard(
    news: GamerNews,
    boardName: String,
    onLinkClick: (Paragraph.Link) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    @Composable
    fun content() {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            GamerNewsCardHeader(news, boardName)
            GamerNewsCardContent(
                news = news,
                onLinkClick = onLinkClick,
                onReplyToClick = { },
                onPreviewReplyTo = { "" },
            )
        }
    }
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            content()
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            content()
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewGamerNewsCard() {
    PreviewTheme {
        GamerNewsCard(
            news = mockGamerNews(),
            boardName = "Board",
            onLinkClick = { },
            onClick = { },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamerReNewsCard(
    reNews: GamerNews,
    onLinkClick: (Paragraph.Link) -> Unit = { },
    onReplyToClick: (Paragraph.ReplyTo) -> Unit = { },
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String  = { "" },
    onClick: (() -> Unit)? = null,
) {
    @Composable
    fun content() {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            GamerReNewsCardHeader(reNews)
            ParagraphBlock(
                reNews.content,
                100,
                onLinkClick = onLinkClick,
                onReplyToClick = onReplyToClick,
                onPreviewReplyTo = onPreviewReplyTo,
            )
        }
    }
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            content()
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            content()
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewGamerReNewsCard() {
    PreviewTheme {
        GamerReNewsCard(
            reNews = mockGamerNews(),
            onLinkClick = { },
            onReplyToClick = { },
            onPreviewReplyTo = { "" },
        )
    }
}

@Composable
private fun GamerNewsCardHeader(news: GamerNews, boardName: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            CardHeadTimeBlock(news.createdAt)
            CardHeadTextBlock("${news.poster}@巴哈/$boardName")
        }
        Row {
            CardHeadRepliesBlock(news.replies)
        }
    }
}

@Composable
private fun GamerReNewsCardHeader(reNews: GamerNews) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            GamerNewsCardTitle(reNews.title!!)
        }
        Row {
            CardHeadTimeBlock(reNews.createdAt)
            CardHeadTextBlock(reNews.poster ?: "")
        }
    }
}

@Composable
private fun GamerNewsCardContent(
    news: GamerNews,
    onLinkClick: (Paragraph.Link) -> Unit,
    onReplyToClick: (Paragraph.ReplyTo) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
        if (news.title.isNullOrEmpty().not()) {
            GamerNewsCardTitle(news.title!!)
        }
        ParagraphBlock(
            news.content,
            100,
            onLinkClick,
            onReplyToClick,
            onPreviewReplyTo,
        )
    }
}

@Composable
private fun GamerNewsCardTitle(text: String) {
    Text(
        text = text,
        style = NewshubTheme.typography.titleMedium,
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}