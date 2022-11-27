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
import self.nesl.newshub.ui.component.AppCard
import self.nesl.newshub.ui.component.ParagraphBlock
import self.nesl.newshub.ui.theme.NewshubTheme


@Composable
fun GamerNewsCard(
    news: GamerNews,
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
            GamerNewsCardHeader(news, boardName)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
            GamerNewsCardTitle(news.title)
            ParagraphBlock(
                article = news.content,
                textLengthMax = 100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
            OriginalLinkParagraph(news.threadUrl, onParagraphClick)
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
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
) {
    AppCard(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            GamerPostCardHeader(post)
            ParagraphBlock(
                post.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
            OriginalLinkParagraph(post.threadUrl, onParagraphClick)
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Composable
fun GamerRePostCard(
    post: GamerPost,
    onParagraphClick: (Paragraph) -> Unit = { },
    onClick: (() -> Unit)? = null,
) {
    AppCard(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.space_8))
        ) {
            GamerPostCardHeader(post)
            ParagraphBlock(
                post.content,
                100,
                onParagraphClick = onParagraphClick,
                onPreviewReplyTo = { "" },
            )
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8)))
}

@Preview
@Composable
private fun PreviewGamerPostCard() {
    NewshubTheme {
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
private fun GamerNewsCardTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}