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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.parentIs
import self.nesl.hub_server.data.post.toText
import self.nesl.hub_server.data.rawImages
import self.nesl.newshub.encode
import self.nesl.newshub.isZeroOrNull
import self.nesl.newshub.ui.component.AppDialog
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.component.gallery.AsyncGallery
import self.nesl.newshub.ui.news.GamerPostCard
import self.nesl.newshub.ui.news.KomicaRePostCard
import self.nesl.newshub.ui.news.KomicaPostCard

@Composable
fun ThreadRoute(
    threadViewModel: ThreadViewModel,
    navController: NavHostController,
){
    val pagingPosts = threadViewModel.pagingPosts.collectAsLazyPagingItems()
    val loading by threadViewModel.loading.collectAsState(false)
    val boardName by threadViewModel.boardName.collectAsState("")
    val replyStack = remember { mutableStateListOf<Post>() }
    val galleryStack = remember { mutableStateListOf<String>() }
    var galleryIndex by remember { mutableStateOf(-1) }
    val context = LocalContext.current

    fun onReplyToClick(replyTo: Paragraph.ReplyTo) {
        pagingPosts.itemSnapshotList.findLast { it?.id?.equals(replyTo.id) ?: false }
            ?.let { replyStack.add(it) }
    }

    fun onLinkClick(link: Paragraph.Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.content))
        startActivity(context, intent, null)
    }

    fun onImageClick(image: Paragraph.ImageInfo) {
        val images = pagingPosts.itemSnapshotList.flatMap { it?.content?.rawImages() ?: emptyList() }
        galleryStack.clear()
        galleryStack.addAll(images)
        galleryIndex = images.indexOf(image.raw)
    }

    fun onParagraphClick(p: Paragraph) {
        when (p) {
            is Paragraph.Link -> onLinkClick(p)
            is Paragraph.ReplyTo -> onReplyToClick(p)
            is Paragraph.ImageInfo -> onImageClick(p)
            else -> { }
        }
    }

    fun onPreviewReplyTo(replyTo: Paragraph.ReplyTo): String {
        return pagingPosts.itemSnapshotList.findLast { it?.id ==  replyTo.id }?.toText() ?: ""
    }

    fun navigateToRePostThread(rePost: Post) {
        if (pagingPosts.itemSnapshotList.items.parentIs(rePost.id).isNotEmpty()) {
            val top = pagingPosts.peek(0)
            top?.let {
                navController.navigate("thread/${it.threadUrl.encode()}/re-post/${rePost.id}")
            }
        }
    }

    ThreadScreen(
        refreshState = rememberSwipeRefreshState(loading),
        pagingPosts = pagingPosts,
        boardName = boardName,
        onParagraphClick = ::onParagraphClick,
        onRefresh = {
            threadViewModel.clear()
            pagingPosts.refresh()
        },
        navigateUp = navController::navigateUp,
        onPreviewReplyTo = ::onPreviewReplyTo,
        navigateToRePostThread = ::navigateToRePostThread,
    )

    if (replyStack.isNotEmpty()) {
        AppDialog(
            onDismissRequest = {
                replyStack.clear()
            },
        ) {
            RePostCard(
                post = replyStack.lastOrNull(),
                navigateToRePostThread = { navigateToRePostThread(replyStack.last()) },
                onParagraphClick = ::onParagraphClick,
                onPreviewReplyTo = ::onPreviewReplyTo,
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

    if (galleryIndex >= 0) {
        AsyncGallery(
            urls = galleryStack,
            startIndex = galleryIndex,
            onDismissRequest = { galleryIndex = -1 }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadScreen(
    refreshState: SwipeRefreshState,
    pagingPosts: LazyPagingItems<Post>,
    boardName: String,
    onRefresh: () -> Unit,
    navigateUp: () -> Unit = {},
    onParagraphClick: (Paragraph) -> Unit = { },
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
    navigateToRePostThread: (Post) -> Unit,
){
    Scaffold(
        topBar = {
            if (pagingPosts.itemSnapshotList.isNotEmpty()) {
                NewsHubTopBar(
                    onBackPressed = { navigateUp() },
                    title = pagingPosts.peek(0)!!.title,
                )
            } else {
                NewsHubTopBar(
                    onBackPressed = { navigateUp() },
                    title = "",
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                pagingPosts.apply {
                    itemsIndexed(this) { index, post ->
                        if (index == 0) {
                            PostCard(
                                post = post,
                                boardName = boardName,
                                onParagraphClick = onParagraphClick,
                            )
                        } else if (post != null) {
                            RePostCard(
                                post = post,
                                navigateToRePostThread = { navigateToRePostThread(post) },
                                onParagraphClick = onParagraphClick,
                                onPreviewReplyTo = onPreviewReplyTo,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostCard(
    post: Post?,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit = {},
) {
    when (post) {
        is KomicaPost -> KomicaPostCard(
            news = post,
            boardName = boardName,
            onParagraphClick = onParagraphClick,
        )
        is GamerPost -> GamerPostCard(
            post = post,
            onParagraphClick = onParagraphClick,
        )
    }
}

@Composable
fun RePostCard(
    post: Post?,
    navigateToRePostThread: (() -> Unit)? = null,
    onParagraphClick: (Paragraph) -> Unit = {},
    onPreviewReplyTo: (Paragraph.ReplyTo) -> String,
) {
    when (post) {
        is KomicaPost -> KomicaRePostCard(
            rePost = post,
            onClick = navigateToRePostThread.takeIf { post.replies.isZeroOrNull().not() },
            onPreviewReplyTo = onPreviewReplyTo,
            onParagraphClick = onParagraphClick,
        )
        is GamerPost -> GamerPostCard(
            post = post,
            onParagraphClick = onParagraphClick,
            onClick = navigateToRePostThread.takeIf { post.replies.isZeroOrNull().not() },
        )
    }
}