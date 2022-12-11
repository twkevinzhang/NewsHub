package self.nesl.newshub.ui.thread

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.ParagraphType
import self.nesl.hub_server.data.post.Post
import self.nesl.hub_server.data.post.gamer.GamerPost
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.parentIs
import self.nesl.hub_server.data.post.toText
import self.nesl.newshub.encode
import self.nesl.newshub.isZeroOrNull
import self.nesl.newshub.model.Thumbnail
import self.nesl.newshub.ui.component.AppDialog
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.component.gallery.Gallery
import self.nesl.newshub.ui.component.gallery.VideoThumbnail
import self.nesl.newshub.ui.component.gallery.ZoomableBox
import self.nesl.newshub.ui.news.GamerPostCard
import self.nesl.newshub.ui.news.GamerRePostCard
import self.nesl.newshub.ui.news.KomicaRePostCard
import self.nesl.newshub.ui.news.KomicaPostCard

@Composable
fun ThreadRoute(
    threadViewModel: ThreadViewModel,
    navController: NavHostController,
){
    val pagingPosts = threadViewModel.pagingPosts.collectAsLazyPagingItems()
    var loading by remember { mutableStateOf(false) }
    val boardName by threadViewModel.boardName.collectAsState("")
    val refreshState = rememberSwipeRefreshState(loading)
    val replyStack = remember { mutableStateListOf<Post>() }
    val gallery = threadViewModel.gallery.collectAsLazyPagingItems()
    var galleryStart by remember { mutableStateOf(-1) }
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
        galleryStart = gallery.itemSnapshotList.map { it?.raw }.indexOf(image.raw)
    }

    fun onVideoThumbClick(video: Paragraph.VideoInfo) {
        galleryStart = gallery.itemSnapshotList.map { it?.raw }.indexOf(video.url)
    }

    fun onVideoPlayClick(thumbnail: Thumbnail) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(thumbnail.raw))
        ContextCompat.startActivity(context, intent, null)
    }

    fun onParagraphClick(p: Paragraph) {
        when (p) {
            is Paragraph.Link -> onLinkClick(p)
            is Paragraph.ReplyTo -> onReplyToClick(p)
            is Paragraph.ImageInfo -> onImageClick(p)
            is Paragraph.VideoInfo -> onVideoThumbClick(p)
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
        refreshState = refreshState,
        pagingPosts = pagingPosts,
        boardName = boardName,
        onParagraphClick = ::onParagraphClick,
        onRefresh = {
            threadViewModel.clear()
            pagingPosts.refresh()
        },
        navigateUp = navController::navigateUp,
        onPreviewReplyTo = ::onPreviewReplyTo,
        onLoadStateChange = { loadState ->
            loadState.apply {
                when {
                    refresh is LoadState.Loading -> {
                        loading = true
                    }
                    refresh is LoadState.NotLoading -> {
                        loading = false
                    }
                    refresh is LoadState.Error -> {
                        loading = false
                    }
                }
            }
        },
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

    if (galleryStart >= 0) {
        Gallery(
            size = gallery.itemCount,
            startIndex = galleryStart,
            onDismissRequest = { galleryStart = -1 }
        ) { page ->
            val thumbnail = gallery.itemSnapshotList.items[page]
            if (thumbnail.mediaType == ParagraphType.IMAGE) {
                var loaded by remember { mutableStateOf(false) }
                ZoomableBox(
                    loaded = loaded,
                ) {
                    SubcomposeAsyncImage(
                        model = thumbnail.url,
                        contentDescription = null,
                        loading = { CircularProgressIndicator() },
                        onSuccess = { loaded = true }
                    )
                }
            } else {
                VideoThumbnail(video = thumbnail) {
                    onVideoPlayClick(thumbnail)
                }
            }
        }
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
    onLoadStateChange: (CombinedLoadStates) -> Unit = { },
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
                LazyColumn {
                    pagingPosts.apply {
                        item {
                            onLoadStateChange(loadState)
                        }
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
}

@Composable
fun PostCard(
    post: Post?,
    boardName: String,
    onParagraphClick: (Paragraph) -> Unit = {},
) {
    when (post) {
        is KomicaPost -> KomicaPostCard(
            post = post,
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
        is GamerPost -> GamerRePostCard(
            post = post,
            onParagraphClick = onParagraphClick,
            onClick = navigateToRePostThread.takeIf { post.replies.isZeroOrNull().not() },
        )
    }
}