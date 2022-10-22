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
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.mockKomicaPost
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaPostCard(
    news: KomicaPost,
    boardName: String,
    onLinkClick: (Paragraph.Link) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            KomicaPostCardContent(
                news = news,
                boardName = boardName,
                onLinkClick = onLinkClick,
                onReplyToClick = { },
                onPreviewReplyTo = { "" },
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            KomicaPostCardContent(
                news = news,
                boardName = boardName,
                onLinkClick = onLinkClick,
                onReplyToClick = { },
                onPreviewReplyTo = { "" },
            )
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
            onLinkClick = { },
            onClick = { },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KomicaRePostCard(
    rePost: KomicaPost,
    onLinkClick: (Paragraph.Link) -> Unit = { },
    onReplyToClick: (Paragraph.ReplyTo) -> Unit = { },
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String  = { "" },
    onClick: (() -> Unit)? = null,
) {
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            KomicaRePostCardContent(
                rePost = rePost,
                onLinkClick = onLinkClick,
                onReplyToClick = onReplyToClick,
                onPreviewReplyTo = onPreviewReplyTo,
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            KomicaRePostCardContent(
                rePost = rePost,
                onLinkClick = onLinkClick,
                onReplyToClick = onReplyToClick,
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
            onLinkClick = { },
            onReplyToClick = { },
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
            CardHeadPosterBlock(rePost.poster)
            CardHeadTimeBlock(rePost.createdAt)
            CardHeadTextBlock(rePost.id)
        }
    }
}

@Composable
private fun KomicaPostCardContent(
    news: KomicaPost,
    boardName: String,
    onLinkClick: (Paragraph.Link) -> Unit,
    onReplyToClick: (Paragraph.ReplyTo) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
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
            onLinkClick = onLinkClick,
            onReplyToClick = onReplyToClick,
            onPreviewReplyTo = onPreviewReplyTo,
        )
    }
}

@Composable
fun KomicaRePostCardContent(
    rePost: KomicaPost,
    onLinkClick: (Paragraph.Link) -> Unit,
    onReplyToClick: (Paragraph.ReplyTo) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
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

@Composable
private fun KomicaNewsCardTitle(text: String) {
    when (text) {
        "無題", "無念" ->
            Text(
                text = text,
                style = NewshubTheme.typography.titleMedium.copy(),
                color = LocalContentColor.current.copy(alpha = AppDisabledAlpha),
            )
        "" -> KomicaNewsCardTitle("無題")
        else ->
            Text(
                text = text,
                style = NewshubTheme.typography.titleMedium,
            )
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}