package self.nesl.newshub.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.paging.compose.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flowOf
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.TopNews
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_head.komica.mockKomicaTopNews
import self.nesl.newshub.R
import self.nesl.newshub.encode
import self.nesl.newshub.ui.component.AppBottomBar
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.navigation.NewsNavItems
import self.nesl.newshub.ui.navigation.TopicNavItems
import self.nesl.newshub.ui.navigation.bottomNavItems
import self.nesl.newshub.ui.news.KomicaTopNewsCard
import self.nesl.newshub.ui.theme.PreviewTheme

@Composable
fun TopicRoute(
    topicViewModel: TopicViewModel,
    navController: NavHostController,
    openDrawer: () -> Unit,
){
    val topic by topicViewModel.topic.collectAsState()
    val pagingTopNews = topicViewModel.pagingTopNews.collectAsLazyPagingItems()
    val enableHosts by topicViewModel.enableHosts.collectAsState(emptyList())
    var loading by remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(loading)
    val navigateToNews = { topNews: TopNews -> navController.navigate(NewsNavItems.NewsThread.route.plus("/${topNews.url.encode()}")) }

    TopicScreen(
        refreshState = refreshState,
        lazyColumnState = pagingTopNews.rememberLazyListState(),
        topic = topic,
        pagingTopNews = pagingTopNews,
        enableHosts = enableHosts,
        openDrawer = openDrawer,
        onRefresh = {
            topicViewModel.clearAllTopNews()
            pagingTopNews.refresh()
        },
        onHostActiveClick = {
            topicViewModel.disableHost(it)
        },
        onHostInactiveClick = {
            topicViewModel.enableHost(it)
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
        topNews = { topNews ->
            when (topNews) {
                is KomicaTopNews -> KomicaTopNewsCard(topNews) { navigateToNews(topNews) }
                else -> Text(text = "not support")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(
    topic: TopicNavItems,
    refreshState: SwipeRefreshState,
    lazyColumnState: LazyListState,
    pagingTopNews: LazyPagingItems<TopNews>,
    enableHosts: List<Host>,
    onHostActiveClick: (Host) -> Unit,
    onHostInactiveClick: (Host) -> Unit,
    openDrawer: () -> Unit,
    onRefresh: () -> Unit,
    onLoadStateChange: (CombinedLoadStates) -> Unit = { },
    topNews: @Composable (TopNews?) -> Unit,
) {
    Scaffold(
        topBar = {
            NewsHubTopBar(
                onMenuPressed = { openDrawer() },
                title = stringResource(id = topic.resourceId)
            )
        },
        bottomBar = { AppBottomBar(bottomNavItems()) },
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
                        HostFilter(
                            selected = enableHosts,
                            onActiveClick = { onHostActiveClick(it) },
                            onInactiveClick = { onHostInactiveClick(it) },
                        )
                    }
                    pagingTopNews.apply {
                        item {
                            onLoadStateChange(loadState)
                        }
                        items(this) { topNews ->
                            topNews(topNews)
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
    val mockNewsfeed = flowOf(PagingData.from(listOf<TopNews>(mockKomicaTopNews()))).collectAsLazyPagingItems()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    PreviewTheme {
        TopicScreen(
            topic = TopicNavItems.Square,
            refreshState = rememberSwipeRefreshState(isRefreshing = false),
            pagingTopNews = mockNewsfeed,
            lazyColumnState = mockNewsfeed.rememberLazyListState(),
            enableHosts = emptyList(),
            openDrawer = { },
            onRefresh = { },
            onHostActiveClick = { },
            onHostInactiveClick = { },
            topNews = { topNews ->
                when (topNews) {
                    is KomicaTopNews -> KomicaTopNewsCard(topNews) { }
                    else -> Text(text = "not support")
                }
            },
        )
    }
}

@Composable
fun HostFilter(
    selected: List<Host> = emptyList(),
    onActiveClick: (Host) -> Unit = { },
    onInactiveClick: (Host) -> Unit = { },
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.space_16)),
    ) {
        Host.values().toList().chunked(4).forEach { hosts ->
            Row {
                hosts.forEach {
                    val active = selected.contains(it)
                    HostIcon(it, active) {
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
fun PreviewHostFilter() {
    PreviewTheme {
        HostFilter(
            selected = listOf(Host.KOMICA),
        )
    }
}

@Composable
fun HostIcon(
    host: Host,
    selected: Boolean = false,
    onClick: () -> Unit = { },
) {
    val (painter, label) = when (host) {
        Host.KOMICA -> {
            painterResource(R.drawable.ic_outline_globe_24) to stringResource(id = R.string.komica)
        }
        Host.GAMER -> {
            painterResource(R.drawable.ic_outline_globe_24) to stringResource(R.string.gamer)
        }
    }

    NavigationRailItem(
        label = { Text(label) },
        icon = { Icon(painter, contentDescription = "") },
        selected = selected,
        onClick = onClick,
    )
}

@Preview
@Composable
fun PreviewHostIcon() {
    PreviewTheme {
        HostIcon(Host.KOMICA)
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