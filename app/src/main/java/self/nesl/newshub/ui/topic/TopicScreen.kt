package self.nesl.newshub.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.news_head.Host
import self.nesl.hub_server.data.news_head.NewsHead
import self.nesl.hub_server.data.news_head.komica.KomicaNewsHead
import self.nesl.newshub.R
import self.nesl.newshub.toHumanTime
import self.nesl.newshub.ui.component.AppBottomBar
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.navigation.TopicNavItems
import self.nesl.newshub.ui.navigation.bottomNavItems
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bindTopicScreen(
    topicViewModel: TopicViewModel,
    navController: NavHostController,
    openDrawer: () -> Unit,
){
    val topic by topicViewModel.topic.collectAsState()
    val newsfeed = topicViewModel.newsfeed.collectAsLazyPagingItems()
    val enableHosts by topicViewModel.enableHosts.collectAsState(emptyList())
    var loading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NewsHubTopBar(
                onMenuPressed = { openDrawer() },
                title = stringResource(id = topic.resourceId)
            )
        },
        bottomBar = { AppBottomBar(bottomNavItems()) },
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = {
                topicViewModel.clearAllNewsHead()
                newsfeed.refresh()
            },
        ) {
            LazyColumn(
                contentPadding = it,
            ) {
                item {
                    HostFilter(
                        selected = enableHosts,
                        onActiveClick = { topicViewModel.disableHost(it) },
                        onInactiveClick = { topicViewModel.enableHost(it) },
                    )
                }
                newsfeed.apply {
                    item {
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
                                    Error(refresh as LoadState.Error)
                                }
                                prepend is LoadState.Loading -> {
                                    loading = true
                                }
                                prepend is LoadState.NotLoading -> {
                                    loading = false
                                }
                                prepend is LoadState.Error -> {
                                    loading = false
                                    Error(prepend as LoadState.Error)
                                }
                            }
                        }
                    }
                    items(this) { newsHead ->
                        when (newsHead) {
                            is KomicaNewsHead -> KomicaNewsHeadCard(newsHead)
                            else -> Text(text = "not support")
                        }
                    }
                    item {
                        Footer()
                    }
                    item {
                        loadState.apply {
                            when {
                                append is LoadState.Loading -> {
                                    loading = true
                                    LoadingFooter()
                                }
                                append is LoadState.NotLoading -> {
                                    loading = false
                                }
                                append is LoadState.Error -> {
                                    loading = false
                                    Error(append as LoadState.Error)
                                }
                            }
                        }
                    }
                }
            }
        }
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
