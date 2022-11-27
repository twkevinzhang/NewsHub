package self.nesl.newshub.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.mockKomicaPost
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.AppCard
import self.nesl.newshub.ui.component.LinkParagraph
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.component.TextParagraph
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaPostCard(
    news: KomicaPost,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    AppCard(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            KomicaPostCardHeader(news, boardName)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            if (news.title.isEmpty().not()) {
                KomicaNewsCardTitle(news.title)
            }
            ParagraphBlock(
                news.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
            OriginalLinkParagraph(news, onParagraphClick)
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewKomicaPostCard() {
    PreviewTheme {
        KomicaPostCard(
            news = mockKomicaPost(),
            boardName = "Board",
            onParagraphClick = { },
            onClick = { },
        )
    }
}

@Composable
fun KomicaRePostCard(
    rePost: KomicaPost,
    onParagraphClick: (Paragraph) -> Unit = { },
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String  = { "" },
    onClick: (() -> Unit)? = null,
) {
    AppCard(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            KomicaRePostCardHeader(rePost)
            ParagraphBlock(
                rePost.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = onPreviewReplyTo,
            )
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewKomicaRePostCard() {
    PreviewTheme {
        KomicaRePostCard(
            rePost = mockKomicaPost(),
            onParagraphClick = { },
            onPreviewReplyTo = { "" },
        )
    }
}

@Composable
private fun KomicaPostCardHeader(news: KomicaPost, boardName: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            CardHeadPosterBlock(news.poster)
            CardHeadTimeBlock(news.createdAt)
            CardHeadTextBlock("${news.id}@Komica/$boardName")
        }
        Row {
            CardHeadRepliesBlock(news.replies)
        }
    }
}

@Composable
private fun KomicaRePostCardHeader(rePost: KomicaPost) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            KomicaNewsCardTitle(rePost.title)
        }
        Row {
            CardHeadTimeBlock(rePost.createdAt)
            CardHeadTextBlock("${rePost.id}(${rePost.poster})")
            CardHeadRepliesBlock(rePost.replies, showZero = false)
        }
    }
}

@Composable
private fun KomicaNewsCardTitle(text: String) {
    when (text) {
        "無題", "無念" ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(),
                color = LocalContentColor.current.copy(alpha = AppDisabledAlpha),
            )
        "" -> KomicaNewsCardTitle("無題")
        else ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}