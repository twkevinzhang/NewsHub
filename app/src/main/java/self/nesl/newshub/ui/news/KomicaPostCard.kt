package self.nesl.newshub.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news.komica.KomicaNews
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.mockKomicaPost
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.AppCard
import self.nesl.newshub.ui.component.LinkParagraph
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.component.TextParagraph
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun KomicaNewsCard(
    news: KomicaNews,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    KomicaPostCardContent(
        threadUrl = news.threadUrl,
        poster = news.poster,
        createdAt = news.createdAt,
        replies = news.replies,
        title = news.title,
        content = news.content,
        boardName = boardName,
        onParagraphClick = onParagraphClick,
        onClick = onClick,
    )
}

@Composable
fun KomicaPostCard(
    news: KomicaPost,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    KomicaPostCardContent(
        threadUrl = news.threadUrl,
        poster = news.poster,
        createdAt = news.createdAt,
        replies = news.replies,
        title = news.title,
        content = news.content,
        boardName = boardName,
        onParagraphClick = onParagraphClick,
        onClick = onClick,
    )
}

@Composable
private fun KomicaPostCardContent(
    threadUrl: String,
    poster: String? = null,
    createdAt: Long? = null,
    id: String? = null,
    replies: Int? = null,
    title: String,
    content: List<Paragraph>,
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
            KomicaPostCardHeader(
                poster = poster,
                createdAt = createdAt,
                id = id,
                replies = replies,
                boardName = boardName,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            if (title.isEmpty().not()) {
                KomicaNewsCardTitle(title)
            }
            ParagraphBlock(
                content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
            OriginalLinkParagraph(threadUrl, onParagraphClick)
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
private fun PreviewKomicaPostCard() {
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
private fun PreviewKomicaRePostCard() {
    PreviewTheme {
        KomicaRePostCard(
            rePost = mockKomicaPost(),
            onParagraphClick = { },
            onPreviewReplyTo = { "" },
        )
    }
}

@Composable
private fun KomicaPostCardHeader(
    poster: String? = null,
    createdAt: Long? = null,
    id: String? = null,
    replies: Int?,
    boardName: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            CardHeadPosterBlock(poster)
            CardHeadTimeBlock(createdAt)
            CardHeadTextBlock("${id}@Komica/$boardName")
        }
        Row {
            CardHeadRepliesBlock(replies)
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