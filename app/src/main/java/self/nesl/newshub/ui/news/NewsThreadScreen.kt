package self.nesl.newshub.ui.news

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import self.nesl.hub_server.data.news_head.TopNews
import self.nesl.hub_server.data.news_head.komica.KomicaTopNews
import self.nesl.hub_server.data.news_thread.NewsThread
import self.nesl.newshub.ui.component.AppBottomBar
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.navigation.bottomNavItems
import self.nesl.newshub.ui.topic.KomicaTopNewsCard

@Composable
fun NewsThreadRoute(
    newsThreadViewModel: NewsThreadViewModel,
    navController: NavHostController,
){
    val newsThread by newsThreadViewModel.newsThread.collectAsState(null)
    val loading by newsThreadViewModel.loading.collectAsState(false)

    NewsThreadScreen(
        refreshState = rememberSwipeRefreshState(loading),
        newsThread = newsThread,
        onRefresh = {
            newsThreadViewModel.refresh()
        },
        navigateUp = { navController.navigateUp() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsThreadScreen(
    refreshState: SwipeRefreshState,
    newsThread: NewsThread? = null,
    onRefresh: () -> Unit,
    navigateUp: () -> Unit = {},
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
                LazyColumn {
                    item {
                        val head = newsThread?.head
                        if (head is KomicaTopNews) {
                            KomicaTopNewsCard(head)
                            newsThread.comments.forEach { comment ->
                                when (comment) {
                                    is KomicaTopNews -> KomicaTopNewsCard(comment)
                                    else -> Log.e("NewsThreadScreen", "unknown comment type")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}