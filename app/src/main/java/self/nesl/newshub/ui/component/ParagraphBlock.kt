package self.nesl.newshub.ui.component

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import self.nesl.hub_server.data.*
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.AppLink
import self.nesl.newshub.ui.theme.AppWhite
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun ParagraphBlock(
    article: List<Paragraph>,
    max: Int? = null
) {
    Column {
        article.map {
            when (it) {
//            ParagraphType.QUOTE -> QuoteBlock(it)
//            ParagraphType.REPLY_TO -> QuoteBlock(it)
                is Paragraph.Text -> TextParagraph(it)
                is Paragraph.ImageInfo -> ImageParagraph(it)
                is Paragraph.Link -> LinkParagraph(it)
                is Paragraph.ReplyTo -> ReplyToParagraph(it)
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewParagraphBlock() {
    PreviewTheme {
        ParagraphBlock(
            article = listOf(
                Paragraph.ReplyTo("Hello World"),
                Paragraph.Text("Hello World"),
                Paragraph.ImageInfo(
                    "https://i.imgur.com/1Z1Z1Z1.jpg",
                    "https://i.imgur.com/1Z1Z1Z1.jpg"
                ),
                Paragraph.Link("https://www.google.com")
            )
        )
    }
}

@Composable
fun TextParagraph(paragraph: Paragraph.Text) {
    Text(
        text = paragraph.content,
        style = NewshubTheme.typography.bodyMedium,
    )
}

@Composable
fun ImageParagraph(paragraph: Paragraph.ImageInfo) {
    SubcomposeAsyncImage(
        model = paragraph.thumb,
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
fun ReplyToParagraph(paragraph: Paragraph.ReplyTo, onClick: () -> Unit = { }) {

}