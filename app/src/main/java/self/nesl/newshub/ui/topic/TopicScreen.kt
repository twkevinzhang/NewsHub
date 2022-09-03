package self.nesl.newshub.ui.topic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.paging.compose.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import self.nesl.newshub.R
import self.nesl.newshub.data.news.Host
import self.nesl.newshub.ui.component.AppMaxWidthItem
import self.nesl.newshub.ui.theme.NewshubTheme

@Composable
fun bindTopicScreen(
    topicViewModel: TopicViewModel,
    navController: NavHostController,
    openDrawer: () -> Unit = { },
){
    val newsfeed = topicViewModel.newsfeed.collectAsLazyPagingItems()
    val enableHosts by topicViewModel.enableHosts.collectAsState(emptyList())

    LazyColumn {
        item {
            HostFilter(
                selected = enableHosts,
                onActiveClick = { topicViewModel.disableHost(it) },
                onInactiveClick = { topicViewModel.enableHost(it) },
            )
        }
        newsfeed.apply {
            item {
                HeaderLoading(loadState = loadState)
            }
            items(this) { news ->
                if (news != null) {
                    NewsHead(news.url.takeLast(10))
                }
            }
            item {
                FooterLoading(loadState = loadState)
            }
        }
    }
}

@Composable
fun NewsHead(news: String) {
    AppMaxWidthItem(
        title = news,
    )
}

@Composable
fun HeaderLoading(loadState: CombinedLoadStates) {
    loadState.apply {
        when {
            refresh is LoadState.Loading -> {
                Loading()
            }
            refresh is LoadState.NotLoading -> {

            }
            refresh is LoadState.Error -> {
                Error(refresh as LoadState.Error)
            }
            prepend is LoadState.Loading -> {
                Loading()
            }
            prepend is LoadState.NotLoading -> {

            }
            prepend is LoadState.Error -> {
                Error(prepend as LoadState.Error)
            }
        }
    }
}

@Composable
fun FooterLoading(loadState: CombinedLoadStates) {
    loadState.apply {
        when {
            append is LoadState.Loading -> {
                Loading()
            }
            append is LoadState.NotLoading -> {

            }
            append is LoadState.Error -> {
                Error(append as LoadState.Error)
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
    NewshubTheme {
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
    HostIcon(Host.KOMICA)
}

@Composable
fun Loading() {
    Text(text = "Loading", fontSize = 26.sp)
}

@Composable
fun Error(error: LoadState.Error) {
    Text(error.error.stackTraceToString())
}