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
import self.nesl.hub_server.data.ImageInfo
import self.nesl.hub_server.data.Link
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.Text
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.NewshubTheme

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
                is Text -> TextParagraph(it)
                is ImageInfo -> ImageParagraph(it)
                is Link -> LinkParagraph(it)
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewParagraphBlock() {
    ParagraphBlock(
        article = listOf(
            Text("Hello World"),
            ImageInfo("https://i.imgur.com/1Z1Z1Z1.jpg", "https://i.imgur.com/1Z1Z1Z1.jpg"),
            Link("https://www.google.com")
        )
    )
}

@Composable
fun TextParagraph(paragraph: Text) {
    Text(
        text = paragraph.content,
        style = NewshubTheme.typography.bodyMedium,
    )
}

@Composable
fun ImageParagraph(paragraph: ImageInfo) {
    SubcomposeAsyncImage(
        model = paragraph.thumb,
        contentDescription = null,
        loading = {
            CircularProgressIndicator()
        },
    )
}

@Composable
fun LinkParagraph(paragraph: Link, onClick: () -> Unit = { }) {
    val annotated = buildAnnotatedString {
        append(paragraph.content)
        val (start, end) = 0 to paragraph.content.length
        addStyle(style = SpanStyle(textDecoration = TextDecoration.Underline), start, end)
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