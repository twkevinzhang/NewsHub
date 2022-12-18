package self.nesl.newshub.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news.komica.KomicaNews
import self.nesl.hub_server.data.news.komica.mockKomicaNews
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.mockKomicaPost
import self.nesl.newshub.R
import self.nesl.newshub.ui.component.AppCard
import self.nesl.newshub.ui.component.ButtonInCard
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.NewshubTheme

@Composable
private fun KomicaCard(
    onClick: (() -> Unit)? = null,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    footer: @Composable () -> Unit,
) {
    AppCard(
        onClick = onClick,
    ) {
        Column {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
            ) {
                header()
                content()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.space_8)),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                footer()
            }
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}


@Composable
fun KomicaNewsCard(
    news: KomicaNews,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    KomicaCard(
        onClick = onClick,
        header = {
            KomicaPostCardHeader(
                poster = news.poster,
                createdAt = news.createdAt,
                id = news.id,
                replies = news.replies,
                boardName = boardName,
            )
        },
        content = {
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
        },
        footer = {
            Row {
                ButtonInCard(
                    onClick = {
                        onParagraphClick(Paragraph.Link(news.threadUrl))
                    },
                    resource = R.drawable.ic_outline_globe_24,
                )
            }
            Row { }
        }
    )
}

@Preview
@Composable
private fun PreviewKomicaNewsCard() {
    NewshubTheme {
        KomicaNewsCard(
            news = mockKomicaNews(),
            boardName = "Board",
            onParagraphClick = { },
            onClick = { },
        )
    }
}

@Composable
fun KomicaPostCard(
    post: KomicaPost,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    KomicaCard(
        onClick = onClick,
        header = {
            KomicaPostCardHeader(
                poster = post.poster,
                createdAt = post.createdAt,
                id = post.id,
                replies = post.replies,
                boardName = boardName,
            )
        },
        content = {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            if (post.title.isEmpty().not()) {
                KomicaNewsCardTitle(post.title)
            }
            ParagraphBlock(
                post.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
        },
        footer = {
            Row {
                ButtonInCard(
                    onClick = {
                        onParagraphClick(Paragraph.Link(post.threadUrl))
                    },
                    resource = R.drawable.ic_outline_globe_24,
                )
            }
            Row {
                ButtonInCard(
                    resource = R.drawable.ic_outline_comment_24,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewKomicaPostCard() {
    NewshubTheme {
        KomicaPostCard(
            post = mockKomicaPost(),
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
    KomicaCard(
        onClick = onClick,
        header = {
            KomicaRePostCardHeader(rePost)
        },
        content = {
            ParagraphBlock(
                rePost.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = onPreviewReplyTo,
            )
        },
        footer = {
            Row { }
            Row {
                ButtonInCard(
                    resource = R.drawable.ic_outline_comment_24,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewKomicaRePostCard() {
    NewshubTheme {
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
            CardHeadTimeBlock(createdAt)
            val posterStr = if (poster.isNullOrBlank().not()) "($poster)" else ""
            CardHeadTextBlock("$id$posterStr@Komica/$boardName")
        }
        Row {
            CardHeadRepliesBlock(replies, showZero = false)
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
            CardHeadRepliesBlock(rePost.replies, showZero = true)
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