package self.nesl.newshub.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import self.nesl.newshub.interactor.toNavItem
import self.nesl.newshub.ui.component.AppDrawerContent
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.topic.EmptyRoute
import self.nesl.newshub.ui.topic.NewsListViewModel
import self.nesl.newshub.ui.topic.NewsListViewModel.Companion.defaultTopicId
import self.nesl.newshub.ui.topic.TopicListViewModel
import self.nesl.newshub.ui.topic.TopicRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bindAppScreen(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    topicListViewModel: TopicListViewModel,
) {
    val systemUiController = rememberSystemUiController()
    val topicList by topicListViewModel.topicList.collectAsState(emptyList())

    systemUiController.setSystemBarsColor(color = NewshubTheme.colors.background)
    NewshubTheme {
        val navigationActions = remember(navController) { AppNavigation(navController) }
        val openDrawer = { scope.launch { drawerState.open() } }
        val closeDrawer = { scope.launch { drawerState.close() } }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    topNavItems = topicList.map { it.toNavItem() },
                    onTopNavItemClick = {
                        navigationActions.navigateWithPop(it.route)
                        closeDrawer()
                    },
                    bottomNavItems = drawerNavItems(),
                    onBottomNavItemClick = {
                        navigationActions.navigateWithPop(it.route)
                        closeDrawer()
                    },
                    currentRoute = "",
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = "topic/{topicId}",
            ) {
                composable(
                    route = "topic/{topicId}",
                    arguments = listOf(
                        navArgument("topicId") {
                            type = NavType.StringType
                            defaultValue = defaultTopicId
                        }
                    ),
                ) {
                    it.arguments?.getString("topicId")?.let {
                        TopicRoute(
                            openDrawer = { openDrawer() },
                            topicId = it,
                        )
                    }
                }

                composable(DrawerNavItems.Home.route) {
                    EmptyRoute(
                        openDrawer = { openDrawer() },
                        title = "Home",
                    )
                }

                composable(DrawerNavItems.History.route) {
                    EmptyRoute(
                        openDrawer = { openDrawer() },
                        title = "History",
                    )
                }
            }
        }
    }
}