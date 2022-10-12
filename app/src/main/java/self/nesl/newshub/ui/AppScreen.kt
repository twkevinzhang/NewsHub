package self.nesl.newshub.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope
import self.nesl.newshub.ui.component.AppDrawerContent
import self.nesl.newshub.ui.navigation.*
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

    NewshubTheme {
        val navigationActions = remember(navController) { AppNavigation(navController) }
        val openDrawer = { scope.launch { drawerState.open() } }
        val closeDrawer = { scope.launch { drawerState.close() } }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    topNavItems = topicNavItems(),
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
                startDestination = "topic/{topic}",
            ) {
                composable(
                    route = "topic/{topic}",
                    arguments = listOf(
                        navArgument("topic") {
                            type = NavType.StringType
                            defaultValue = "square"
                        }
                    ),
                ) {
                    it.arguments?.getString("topic")?.let {
                        TopicRoute(
                            openDrawer = { openDrawer() },
                            topic = "topic/${it}".toTopicNavItem(),
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