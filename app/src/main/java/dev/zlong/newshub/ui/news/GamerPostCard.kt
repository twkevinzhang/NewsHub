package dev.zlong.newshub.ui.news

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.zlong.hub_server.data.Paragraph
import dev.zlong.hub_server.data.news.gamer.GamerNews
import dev.zlong.hub_server.data.news.gamer.mockGamerNews
import dev.zlong.hub_server.data.post.gamer.GamerPost
import dev.zlong.hub_server.data.post.gamer.mockGamerPost
import dev.zlong.newshub.R
import dev.zlong.newshub.ui.component.AppCard
import dev.zlong.newshub.ui.component.ButtonInCard
import dev.zlong.newshub.ui.component.LinkParagraph
import dev.zlong.newshub.ui.component.ParagraphBlock
import dev.zlong.newshub.ui.theme.NewshubTheme


@Composable
private fun GamerCard(
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
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.space_8)),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                footer()
            }
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Composable
fun GamerNewsCard(
    news: GamerNews,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
    onClick: (() -> Unit)? = null,
) {
    GamerCard(
        onClick = onClick,
        header = {
            GamerNewsCardHeader(news, boardName)
        },
        content = {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            GamerNewsCardTitle(news.title)
            ParagraphBlock(
                article = news.content,
                textLengthMax = 100,
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
private fun PreviewGamerNewsCard() {
    NewshubTheme {
        GamerNewsCard(
            news = mockGamerNews(),
            boardName = "Board",
            onParagraphClick = { },
            onClick = { },
        )
    }
}

@Composable
fun GamerPostCard(
    post: GamerPost,
    onParagraphClick: (Paragraph) -> Unit = { },
    onClick: (() -> Unit)? = null,
    onMoreCommentsClick: (() -> Unit)? = null,
) {
    GamerCard(
        onClick = onClick,
        header = {
            GamerPostCardHeader(post)
        },
        content = {
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
                    onClick = onMoreCommentsClick,
                    resource = R.drawable.ic_outline_comment_24,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewGamerPostCard() {
    NewshubTheme {
        GamerPostCard(
            post = mockGamerPost(),
            onParagraphClick = { },
            onMoreCommentsClick = { },
        )
    }
}

@Composable
fun GamerRePostCard(
    post: GamerPost,
    onParagraphClick: (Paragraph) -> Unit = { },
    onClick: (() -> Unit)? = null,
    onMoreCommentsClick: (() -> Unit)? = null,
) {
    GamerCard(
        onClick = onClick,
        header = {
            GamerPostCardHeader(post)
        },
        content = {
            ParagraphBlock(
                post.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
        },
        footer = {
            Row { }
            Row {
                ButtonInCard(
                    onClick = onMoreCommentsClick,
                    resource = R.drawable.ic_outline_comment_24,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewGamerRePostCard() {
    NewshubTheme {
        GamerRePostCard(
            post = mockGamerPost(),
            onParagraphClick = { },
            onMoreCommentsClick = { },
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
            CardHeadTextBlock(news.createdAt)
            CardHeadTextBlock("${news.posterName}@巴哈/$boardName")
        }
        Row {
            CardHeadTextBlock("${news.interactions}/${news.popularity}")
        }
    }
}

@Composable
private fun GamerPostCardHeader(post: GamerPost) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CardHeadTextBlock(post.posterName)
        CardHeadTimeBlock(post.createdAt)
    }
}

@Composable
private fun GamerNewsCardTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}