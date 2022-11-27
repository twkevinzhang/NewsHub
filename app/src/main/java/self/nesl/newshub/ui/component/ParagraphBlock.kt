package self.nesl.newshub.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import self.nesl.hub_server.data.*
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.*

@Composable
fun ParagraphBlock(
    article: List<Paragraph>,
    textLengthMax: Int? = null,
    onParagraphClick: (Paragraph) -> Unit,
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    Column {
        article.map {
            when (it) {
                is Paragraph.Text -> TextParagraph(it)
                is Paragraph.ImageInfo -> ImageParagraph(it) { onParagraphClick(it) }
                is Paragraph.Link -> LinkParagraph(it) { onParagraphClick(it) }
                is Paragraph.ReplyTo -> ReplyToParagraph(it, onPreviewReplyTo) { onParagraphClick(it) }
                is Paragraph.Quote -> QuoteParagraph(it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewParagraphBlock() {
    NewshubTheme {
        ParagraphBlock(
            article = listOf(
                Paragraph.ReplyTo("Kevin"),
                Paragraph.Quote("Hi Google"),
                Paragraph.Text("Hi Kevin, welcome!"),
                Paragraph.ImageInfo(
                    "https://i.imgur.com/1Z1Z1Z1.jpg",
                    "https://i.imgur.com/1Z1Z1Z1.jpg"
                ),
                Paragraph.Text("see about us:"),
                Paragraph.Link("https://www.google.com")
            ),
            onParagraphClick = { },
            onPreviewReplyTo = { "" }
        )
    }
}

@Composable
fun TextParagraph(paragraph: Paragraph.Text) {
    Text(
        text = paragraph.content,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun ImageParagraph(paragraph: Paragraph.ImageInfo, onClick: () -> Unit = { }) {
    SubcomposeAsyncImage(
        model = paragraph.thumb,
        modifier = Modifier
            .size(80.dp, 80.dp)
            .clickable(onClick = onClick),
        contentDescription = null,
        loading = {
            CircularProgressIndicator()
        },
    )
}

@Composable
fun LinkParagraph(paragraph: Paragraph.Link, onClick: () -> Unit = { }) {
    val annotated = buildAnnotatedString {
        append(paragraph.content)
        val (start, end) = 0 to paragraph.content.length
        addStyle(style = SpanStyle(color = AppLink, textDecoration = TextDecoration.Underline), start, end)
        addStringAnnotation(
            tag = paragraph.content,
            annotation = paragraph.content,
            start = start,
            end = end
        )
    }
    ClickableText(
        text = annotated,
        onClick = { onClick() },
    )
}

@Composable
fun ReplyToParagraph(paragraph: Paragraph.ReplyTo, onPreviewReplyTo: (Paragraph.ReplyTo) -> String, onClick: () -> Unit = { }) {
    val preview = onPreviewReplyTo(paragraph)
    val annotations = if (preview.isBlank()) {
        ">>${paragraph.id}"
    } else {
        ">>${paragraph.id}($preview...)"
    }
    LinkParagraph(Paragraph.Link(annotations), onClick)
}

@Composable
fun QuoteParagraph(paragraph: Paragraph.Quote) {
    Text(
        text = paragraph.content,
        style = MaterialTheme.typography.bodyMedium,
        color = AppQuote,
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_4)))
}