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
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.gamer.mockGamerPost
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
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            GamerNewsCardContent(
                news = news,
                boardName = boardName,
                onLinkClick = onLinkClick,
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            GamerNewsCardContent(
                news = news,
                boardName = boardName,
                onLinkClick = onLinkClick,
            )
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
fun GamerRePostCard(
    rePost: GamerPost,
    onLinkClick: (Paragraph.Link) -> Unit = { },
    onClick: (() -> Unit)? = null,
) {
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            GamerRePostCardContent(
                post = rePost,
                onLinkClick = onLinkClick,
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            GamerRePostCardContent(
                post = rePost,
                onLinkClick = onLinkClick,
            )
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewGamerRePostCard() {
    PreviewTheme {
        GamerRePostCard(
            rePost = mockGamerPost(),
            onLinkClick = { },
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
private fun GamerRePostCardHeader(rePost: GamerPost) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            GamerNewsCardTitle(rePost.title)
        }
        Row {
            CardHeadTimeBlock(rePost.createdAt)
            CardHeadTextBlock(rePost.posterName ?: "")
        }
    }
}

@Composable
private fun GamerNewsCardContent(
    news: GamerNews,
    boardName: String,
    onLinkClick: (Paragraph.Link) -> Unit,
) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
    ) {
        GamerNewsCardHeader(news, boardName)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
        if (news.title.isEmpty().not()) {
            GamerNewsCardTitle(news.title)
        }
        ParagraphBlock(
            article = news.content,
            max = 100,
            onLinkClick = onLinkClick,
            onReplyToClick = { },
            onPreviewReplyTo = { _ -> "" },
        )
    }
}

@Composable
private fun GamerRePostCardContent(
    post: GamerPost,
    onLinkClick: (Paragraph.Link) -> Unit,
) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
    ) {
        GamerRePostCardHeader(post)
        ParagraphBlock(
            post.content,
            100,
            onLinkClick = onLinkClick,
            onReplyToClick = { },
            onPreviewReplyTo = { "" },
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