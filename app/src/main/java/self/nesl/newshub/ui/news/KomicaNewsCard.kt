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
    onClick: (() -> Unit)? = null,
) {
    @Composable
    fun content() {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            KomicaTopNewsCardHeader(topNews)
            KomicaTopNewsCardContent(
                topNews = topNews,
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
fun PreviewKomicaTopNewsCard() {
    PreviewTheme {
        KomicaTopNewsCard(
            topNews = mockKomicaTopNews(),
            onLinkClick = { },
            onClick = { },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaRePostCard(
    rePost: KomicaTopNews,
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
            KomicaRePostCardHeader(rePost)
            ParagraphBlock(
                rePost.content,
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
fun PreviewKomicaRePostCard() {
    PreviewTheme {
        KomicaRePostCard(
            rePost = mockKomicaTopNews(),
            onLinkClick = { },
            onReplyToClick = { },
            onPreviewReplyTo = { "" },
        )
    }
}

@Composable
private fun KomicaTopNewsCardHeader(topNews: KomicaTopNews) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            CardHeadPosterBlock(topNews.poster)
            CardHeadTimeBlock(topNews.createdAt)
            CardHeadTextBlock("${topNews.id}@Komica")
        }
        Row {
            CardHeadRepliesBlock(topNews.replies)
        }
    }
}

@Composable
private fun KomicaRePostCardHeader(rePost: KomicaTopNews) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            KomicaNewsCardTitle(rePost.title!!)
        }
        Row {
            CardHeadPosterBlock(rePost.poster)
            CardHeadTimeBlock(rePost.createdAt)
            CardHeadTextBlock(rePost.id)
        }
    }
}

@Composable
private fun KomicaTopNewsCardContent(
    topNews: KomicaTopNews,
    onLinkClick: (Paragraph.Link) -> Unit,
    onReplyToClick: (Paragraph.ReplyTo) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    Column {
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

@Composable
private fun KomicaNewsCardTitle(text: String) {
    when (text) {
        "無題", "無念" ->
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