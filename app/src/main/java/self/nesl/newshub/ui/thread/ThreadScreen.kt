package self.nesl.newshub.ui.thread

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.thread.*
import self.nesl.newshub.encode
import self.nesl.newshub.ui.component.AppDialog
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.navigation.NewsNavItems
import self.nesl.newshub.ui.news.KomicaRePostCard
import self.nesl.newshub.ui.news.KomicaPostCard

@Composable
fun ThreadRoute(
    threadViewModel: ThreadViewModel,
    navController: NavHostController,
){
    val thread by threadViewModel.thread.collectAsState(null)
    val loading by threadViewModel.loading.collectAsState(false)
    val replyStack = remember { mutableStateListOf<Post>() }
    val context = LocalContext.current

    fun onKomicaReplyToClick(replyTo: Paragraph.ReplyTo) {
        thread?.rePosts
            ?.findLast { it.url.contains(replyTo.id) }
            ?.let { replyStack.add(it) }
    }

    fun onLinkClick(link: Paragraph.Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.content))
        startActivity(context, intent, null)
    }

    fun onPreviewReplyTo(replyTo: Paragraph.ReplyTo): String {
        return thread?.rePosts?.findLast { it.id ==  replyTo.id }?.toText() ?: ""
    }

    fun onRePostClick(rePost: Post) {
        thread?.let {
            navController.navigate(NewsNavItems.Thread.route.plus("/${it.url.encode()}/re_post/${rePost.id}"))
        }
    }

    ThreadScreen(
        refreshState = rememberSwipeRefreshState(loading),
        thread = thread,
        onRefresh = { threadViewModel.refresh() },
        navigateUp = { navController.navigateUp() },
        onLinkClick = { onLinkClick(it) },
        onKomicaReplyToClick = { onKomicaReplyToClick(it) },
        onPreviewReplyTo = { onPreviewReplyTo(it) },
        onRePostClick = { onRePostClick(it) },
    )

    if (replyStack.isNotEmpty()) {
        AppDialog(
            onDismissRequest = {
                replyStack.clear()
            },
        ) {
            RePostCard(
                post = replyStack.lastOrNull(),
                onClick = { onRePostClick(replyStack.last()) },
                onLinkClick = { onLinkClick(it) },
                onKomicaReplyToClick = { onKomicaReplyToClick(it) },
                onPreviewReplyTo = { onPreviewReplyTo(it) },
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
fun ThreadScreen(
    refreshState: SwipeRefreshState,
    thread: Thread? = null,
    onRefresh: () -> Unit,
    navigateUp: () -> Unit = {},
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
    onRePostClick: (Post) -> Unit,
){
    Scaffold(
        topBar = {
            NewsHubTopBar(
                onBackPressed = { navigateUp() },
                title = thread?.post?.title ?: "",
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
                if (thread != null) {
                    LazyColumn {
                        item {
                            when (val head = thread.post) {
                                is KomicaPost -> KomicaPostCard(
                                    news = head,
                                    onLinkClick = onLinkClick,
                                )
                            }
                        }
                        thread.rePosts.forEach { rePost ->
                            item {
                                RePostCard(
                                    post = rePost,
                                    onClick = { onRePostClick(rePost) },
                                    onLinkClick = onLinkClick,
                                    onKomicaReplyToClick = onKomicaReplyToClick,
                                    onPreviewReplyTo = onPreviewReplyTo,
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
fun RePostCard(
    post: Post?,
    onClick: () -> Unit = {},
    onLinkClick: (Paragraph.Link) -> Unit = {},
    onKomicaReplyToClick: (Paragraph.ReplyTo) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    when (post) {
        is KomicaPost -> KomicaRePostCard(
            rePost = post,
            onClick = onClick,
            onLinkClick = onLinkClick,
            onReplyToClick = onKomicaReplyToClick,
            onPreviewReplyTo = onPreviewReplyTo,
        )
        else -> {}
    }
}