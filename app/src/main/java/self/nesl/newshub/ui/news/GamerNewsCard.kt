package self.nesl.newshub.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamerNewsCard(
    news: GamerNews,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
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
                onParagraphClick = onParagraphClick,
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            GamerNewsCardContent(
                news = news,
                boardName = boardName,
                onParagraphClick = onParagraphClick,
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
            onParagraphClick = { },
            onClick = { },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamerPostCard(
    post: GamerPost,
    onParagraphClick: (Paragraph) -> Unit = { },
    onClick: (() -> Unit)? = null,
) {
    if (onClick != null) {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
            onClick = onClick,
        ) {
            GamerPostCardContent(
                post = post,
                onParagraphClick = onParagraphClick,
            )
        }
    } else {
        Surface(
            tonalElevation = dimensionResource(id = R.dimen.space_2),
        ) {
            GamerPostCardContent(
                post = post,
                onParagraphClick = onParagraphClick,
            )
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
fun PreviewGamerPostCard() {
    PreviewTheme {
        GamerPostCard(
            post = mockGamerPost(),
            onParagraphClick = { },
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
private fun GamerNewsCardContent(
    news: GamerNews,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit,
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
            textLengthMax = 100,
            onParagraphClick = onParagraphClick,
            onPreviewReplyTo = { "" },
        )
        OriginalLinkParagraph(news, onParagraphClick)
    }
}

@Composable
private fun GamerPostCardContent(
    post: GamerPost,
    onParagraphClick: (Paragraph) -> Unit,
) {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
    ) {
        GamerPostCardHeader(post)
        if (post.title.isNotEmpty()) {
            GamerNewsCardTitle(post.title)
        }
        ParagraphBlock(
            post.content,
            100,
            onParagraphClick = {

            },
            onPreviewReplyTo = { "" },
        )
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