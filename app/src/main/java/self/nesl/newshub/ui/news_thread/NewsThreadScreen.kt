package self.nesl.newshub.ui.news_thread

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_thread.*
import self.nesl.newshub.ui.component.AppDialog
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.news.KomicaCommentCard
import self.nesl.newshub.ui.news.KomicaTopNewsCard

@Composable
fun NewsThreadRoute(
    newsThreadViewModel: NewsThreadViewModel,
    navController: NavHostController,
){
    val newsThread by newsThreadViewModel.newsThread.collectAsState(null)
    val loading by newsThreadViewModel.loading.collectAsState(false)
    val replyStack = remember { mutableStateListOf<Comment>() }
    val context = LocalContext.current

    fun onKomicaReplyToClick(replyTo: Paragraph.ReplyTo) {
        newsThread?.comments
            ?.findLast { it.url.contains(replyTo.content) }
            ?.let { replyStack.add(it) }
    }

    fun onLinkClick(link: Paragraph.Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.content))
        startActivity(context, intent, null)
    }

    fun onPreviewReplyTo(replyTo: Paragraph.ReplyTo): String {
        return newsThread?.comments?.replyFor(replyTo.content)?.get(0)?.toText() ?: ""
    }

    NewsThreadScreen(
        refreshState = rememberSwipeRefreshState(loading),
        newsThread = newsThread,
        onRefresh = {
            newsThreadViewModel.refresh()
        },
        navigateUp = { navController.navigateUp() },
        onLinkClick = { onLinkClick(it) },
        onKomicaReplyToClick = { onKomicaReplyToClick(it) },
        onPreviewReplyTo = { onPreviewReplyTo(it) },
    )

    if (replyStack.isNotEmpty()) {
        AppDialog(
            onDismissRequest = {
                replyStack.clear()
            },
        ) {
            CommentsCard(
                comment = replyStack.lastOrNull(),
                onLinkClick = { onLinkClick(it) },
                onKomicaReplyToClick = { onKomicaReplyToClick(it) },
                onPreviewReplyTo = { onPreviewReplyTo(it) }
            )
            Row {
                if (replyStack.size > 1) {
                    Button(onClick = { replyStack.removeLast() }) {
                        Text(text = "Prev")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsThreadScreen(
    refreshState: SwipeRefreshState,
    newsThread: NewsThread? = null,
    onRefresh: () -> Unit,
    navigateUp: () -> Unit = {},
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
){
    Scaffold(
        topBar = {
            NewsHubTopBar(
                onBackPressed = { navigateUp() },
                title = newsThread?.head?.title ?: "",
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = onRefresh,
            ) {
                if (newsThread != null) {
                    LazyColumn {
                        item {
                            when (val head = newsThread.head) {
                                is KomicaTopNews -> KomicaTopNewsCard(
                                    topNews = head,
                                    onLinkClick = onLinkClick,
                                )
                            }
                        }
                        newsThread.comments.forEach { comment ->
                            item {
                                CommentsCard(
                                    comment,
                                    onLinkClick = onLinkClick,
                                    onKomicaReplyToClick = onKomicaReplyToClick,
                                    onPreviewReplyTo = onPreviewReplyTo
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsCard(
    comment: Comment?,
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    when (comment) {
        is KomicaTopNews -> KomicaCommentCard(
            comment = comment,
            onLinkClick = onLinkClick,
            onReplyToClick = onKomicaReplyToClick,
            onPreviewReplyTo = onPreviewReplyTo,
        )
        else -> {}
    }
}
