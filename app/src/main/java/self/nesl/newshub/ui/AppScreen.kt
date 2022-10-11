package self.nesl.newshub.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import self.nesl.newshub.ui.component.AppDrawerContent
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.ui.news_thread.NewsThreadViewModel
import self.nesl.newshub.ui.news_thread.NewsThreadRoute
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.topic.EmptyRoute
import self.nesl.newshub.ui.topic.TopicRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bindAppScreen(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
) {
    val defaultNav = DrawerNavItems.Home

    NewshubTheme {
        val navigationActions = remember(navController) { AppNavigation(navController) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: defaultNav.route
        val openDrawer = { scope.launch { drawerState.open() } }
        val closeDrawer = { scope.launch { drawerState.close() } }

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                AppDrawerContent(
                    topNavItems = topicNavItems(),
                    onTopNavItemClick = {
                        navigationActions.navigateWithPop(it)
                        closeDrawer()
                    },
                    bottomNavItems = drawerNavItems(),
                    currentRoute = currentRoute,
                )
            }
        ) {
            Surface {
                NavHost(
                    navController = navController,
                    startDestination = defaultNav.route,
                ) {
                    composable(DrawerNavItems.Home.route) {
                        EmptyRoute(
                            openDrawer = { openDrawer() },
                            title = "Home",
                        )
                    }

                    composable("topic/{topic}") {
                        it.arguments?.getString("topic")?.let { topic ->
                            TopicRoute(
                                openDrawer = { openDrawer() },
                                topic = topic.toTopic(),
                            )
                        }
                    }
                }
            }
        }
    }
}