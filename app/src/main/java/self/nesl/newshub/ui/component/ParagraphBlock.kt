package self.nesl.newshub.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.ParagraphType
import self.nesl.newshub.R

@Composable
fun ParagraphBlock(
    article: List<Paragraph>,
    max: Int? = null
) {
    Column {
        article.map {
            when (it.type) {
//            ParagraphType.QUOTE -> QuoteBlock(it)
//            ParagraphType.REPLY_TO -> QuoteBlock(it)
                ParagraphType.TEXT -> TextParagraph(it)
                ParagraphType.IMAGE -> ImageParagraph(it)
                ParagraphType.LINK -> LinkParagraph(it)
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
            Paragraph(
                type = ParagraphType.TEXT,
                content = "Hello World"
            ),
            Paragraph(
                type = ParagraphType.IMAGE,
                content = "https://i.imgur.com/1Z1Z1Z1.jpg"
            ),
            Paragraph(
                type = ParagraphType.LINK,
                content = "https://www.google.com"
            )
        )
    )
}

@Composable
fun TextParagraph(paragraph: Paragraph) {
    Text(text = paragraph.content)
}

@Composable
fun ImageParagraph(paragraph: Paragraph) {
    SubcomposeAsyncImage(
        model = paragraph.content,
        contentDescription = null,
        loading = {
            CircularProgressIndicator()
        },
        onSuccess = {
            Log.d("ImageParagraph", "load ${paragraph.content} success")
        },
        onError = {
            Log.e("ImageParagraph", "load ${paragraph.content} error, ${it.result.throwable.stackTraceToString()}")
        }
    )
}

@Composable
fun LinkParagraph(paragraph: Paragraph, onClick: () -> Unit = { }) {
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