package self.nesl.newshub.ui.topic

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.paging.compose.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fragula2.compose.FragulaNavHost
import com.fragula2.compose.swipeable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import self.nesl.hub_server.data.Paragraph
import self.nesl.hub_server.data.Host
import self.nesl.hub_server.data.board.Board
import self.nesl.hub_server.data.news.News
import self.nesl.hub_server.data.news.gamer.GamerNews
import self.nesl.hub_server.data.post.komica.KomicaPost
import self.nesl.hub_server.data.post.komica.mockKomicaPost
import self.nesl.newshub.R
import self.nesl.newshub.encode
import self.nesl.newshub.interactor.Topic
import self.nesl.newshub.thenIfNotNull
import self.nesl.newshub.ui.component.AppBottomBar
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.navigation.bottomNavItems
import self.nesl.newshub.ui.news.GamerNewsCard
import self.nesl.newshub.ui.news.KomicaPostCard
import self.nesl.newshub.ui.thread.ThreadRoute
import self.nesl.newshub.ui.thread.ThreadViewModel
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun TopicRoute(
    openDrawer: () -> Unit,
    topicId: String,
) {
    val navController = rememberNavController()
    FragulaNavHost(
        navController = navController,
        startDestination = "list",
    ) {
        swipeable("list") {
            val factory = HiltViewModelFactory(LocalContext.current, it)
            val newsListViewModel = viewModel<NewsListViewModel>(factory = factory)
            newsListViewModel.topicId(topicId)
            NewsListRoute(
                newsListViewModel = newsListViewModel,
                navController = navController,
                openDrawer = { openDrawer() },
            )
        }

        swipeable("thread/{url}") {
            val factory = HiltViewModelFactory(LocalContext.current, it)
            val threadViewModel = viewModel<ThreadViewModel>(factory = factory)
            it.arguments?.getString("url")?.let { url ->
                threadViewModel.threadUrl(url)
            }
            ThreadRoute(
                threadViewModel = threadViewModel,
                navController = navController,
            )
        }

        swipeable("thread/{url}/re-post/{rePostId}") {
            val factory = HiltViewModelFactory(LocalContext.current, it)
            val threadViewModel = viewModel<ThreadViewModel>(factory = factory)
            it.arguments?.getString("url")?.let { url ->
                threadViewModel.threadUrl(url)
            }
            it.arguments?.getString("rePostId")?.let { rePostId ->
                threadViewModel.rePostId(rePostId)
            }
            ThreadRoute(
                threadViewModel = threadViewModel,
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListRoute(
    newsListViewModel: NewsListViewModel,
    navController: NavHostController,
    openDrawer: () -> Unit,
){
    val topic by newsListViewModel.topic.collectAsState(null)
    val pagingNews = newsListViewModel.pagingNews.collectAsLazyPagingItems()
    val allBoards by newsListViewModel.allBoards.collectAsState(emptyList())
    val enableBoards by newsListViewModel.enableBoards.collectAsState(emptyList())
    var loading by remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(loading)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navigateToNews = { news: News -> navController.navigate("thread/${news.url.encode()}") }
    val context = LocalContext.current

    fun onLinkClick(link: Paragraph.Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.content))
        ContextCompat.startActivity(context, intent, null)
    }

    fun onImageClick(image: Paragraph.ImageInfo) {
    }

    TopicScreen(
        scrollBehavior = scrollBehavior,
        refreshState = refreshState,
        lazyColumnState = pagingNews.rememberLazyListState(),
        topic = topic,
        pagingNews = pagingNews,
        allBoards = allBoards,
        enableBoards = enableBoards,
        openDrawer = openDrawer,
        onRefresh = {
            newsListViewModel.clearAllNews()
            pagingNews.refresh()
        },
        onActiveBoardClick = {
            newsListViewModel.disableBoard(it)
        },
        onInactiveBoardClick = {
            newsListViewModel.enableBoard(it)
        },
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
                    prepend is LoadState.Loading -> {
                        loading = true
                    }
                    prepend is LoadState.NotLoading -> {
                        loading = false
                    }
                    prepend is LoadState.Error -> {
                        loading = false
                    }
                }
            }
        },
        news = { news ->
            var boardName by remember { mutableStateOf("") }
            LaunchedEffect(news) {
                news?.let { boardName = newsListViewModel.readBoardName(it.boardUrl) }
            }
            when (news) {
                is KomicaPost -> KomicaPostCard(
                    news = news,
                    boardName = boardName,
                    onParagraphClick = {
                        when (it) {
                            is Paragraph.Link -> onLinkClick(it)
                            is Paragraph.ImageInfo -> onImageClick(it)
                            else -> { }
                        }
                    },
                    onClick = { navigateToNews(news) },
                )
                is GamerNews -> GamerNewsCard(
                    news = news,
                    boardName = boardName,
                    onParagraphClick = {
                        when (it) {
                        is Paragraph.Link -> onLinkClick(it)
                        else -> { }
                        }
                    },
                    onClick = { navigateToNews(news) },
                )
                else -> Text(text = "not support")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(
    topic: Topic? = null,
    refreshState: SwipeRefreshState,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    lazyColumnState: LazyListState,
    pagingNews: LazyPagingItems<News>,
    allBoards: List<Board>,
    enableBoards: List<Board>,
    onActiveBoardClick: (Board) -> Unit,
    onInactiveBoardClick: (Board) -> Unit,
    openDrawer: () -> Unit,
    onRefresh: () -> Unit,
    onLoadStateChange: (CombinedLoadStates) -> Unit = { },
    news: @Composable (News?) -> Unit,
) {
    Scaffold(
        topBar = {
            NewsHubTopBar(
                onMenuPressed = { openDrawer() },
                title = topic?.name ?: "",
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = { AppBottomBar(bottomNavItems()) },
        modifier = Modifier.thenIfNotNull(scrollBehavior) { nestedScroll(it.nestedScrollConnection) }
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
                LazyColumn(
                    state = lazyColumnState,
                ) {
                    item {
                        BoardFilter(
                            boards = allBoards,
                            selected = enableBoards,
                            onActiveClick = { onActiveBoardClick(it) },
                            onInactiveClick = { onInactiveBoardClick(it) },
                        )
                    }
                    pagingNews.apply {
                        item {
                            onLoadStateChange(loadState)
                        }
                        items(this) { news ->
                            news(news)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewTopicScreen() {
    val mockNewsfeed = flowOf(PagingData.from(listOf<News>(mockKomicaPost()))).collectAsLazyPagingItems()

    PreviewTheme {
        TopicScreen(
            topic = Topic("Square", "Square"),
            refreshState = rememberSwipeRefreshState(isRefreshing = false),
            pagingNews = mockNewsfeed,
            lazyColumnState = mockNewsfeed.rememberLazyListState(),
            allBoards = emptyList(),
            enableBoards = emptyList(),
            openDrawer = { },
            onRefresh = { },
            onActiveBoardClick = { },
            onInactiveBoardClick = { },
            news = { news ->
                KomicaPostCard(
                    news = news as KomicaPost,
                    boardName = "Board",
                    onParagraphClick = { },
                    onClick = { }
                )
            },
        )
    }
}

@Composable
fun BoardFilter(
    boards: List<Board> = emptyList(),
    selected: List<Board> = emptyList(),
    onActiveClick: (Board) -> Unit = { },
    onInactiveClick: (Board) -> Unit = { },
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.space_16)),
    ) {
        boards.chunked(4).forEach { boards ->
            Row {
                boards.forEach {
                    val active = selected.contains(it)
                    BoardIcon(it, active) {
                        if (active) {
                            onActiveClick(it)
                        } else {
                            onInactiveClick(it)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBoardFilter() {
    val boards = listOf(
        Board(name = "A", url = "a.com", host = Host.KOMICA),
        Board(name = "B", url = "b.com", host = Host.GAMER),
        Board(name = "C", url = "c.com", host = Host.GAMER),
        Board(name = "D", url = "d.com", host = Host.GAMER),
        Board(name = "E", url = "e.com", host = Host.GAMER),
    )
    PreviewTheme {
        BoardFilter(
            boards = boards,
            selected = boards.subList(1, 2),
        )
    }
}

@Composable
fun BoardIcon(
    board: Board,
    selected: Boolean = false,
    onClick: () -> Unit = { },
) {
    NavigationRailItem(
        label = { Text(board.name) },
        icon = { Icon(painterResource(R.drawable.ic_outline_globe_24), contentDescription = "") },
        selected = selected,
        onClick = onClick,
    )
}

@Preview
@Composable
fun PreviewBoardIcon() {
    PreviewTheme {
        BoardIcon(Board(name = "A", url = "a.com", host = Host.KOMICA))
    }
}

@Composable
fun LoadingFooter() {
    Text(text = "Loading", fontSize = 26.sp)
}

@Composable
fun Footer() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_65)))
}

@Composable
fun Error(error: LoadState.Error) {
    Text(error.error.stackTraceToString())
}

/**
 * Paging lib has a issue about "Scroll position of LazyColumn built with `collectAsLazyPagingItems` is lost when using Navigation."
 * issue link: https://issuetracker.google.com/issues/177245496#comment24
 */
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}