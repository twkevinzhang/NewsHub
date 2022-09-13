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
import self.nesl.newshub.ui.component.AppDrawerContent
import self.nesl.newshub.ui.topic.TopicViewModel
import self.nesl.newshub.ui.topic.TopicRoute
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.ui.topic.bindEmptyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bindAppScreen() {
    val defaultNav = DrawerNavItems.Home

    NewshubTheme {
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()
        val navigationActions = remember(navController) { AppNavigation(navController) }
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val openDrawer = { scope.launch { drawerState.open() } }
        val closeDrawer = { scope.launch { drawerState.close() } }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: defaultNav.route

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
                        bindEmptyScreen(
                            openDrawer = { openDrawer() },
                        )
                    }

                    composable(TopicNavItems.Square.route) {
                        val factory = HiltViewModelFactory(LocalContext.current, it)
                        val topicViewModel = viewModel<TopicViewModel>(factory = factory)
                        topicViewModel.topic(TopicNavItems.Square)
                        TopicRoute(
                            topicViewModel = topicViewModel,
                            navController = navController,
                            openDrawer = { openDrawer() },
                        )
                    }

                    composable(TopicNavItems.Movie.route) {
                        bindEmptyScreen(
                            openDrawer = { openDrawer() },
                        )
                    }
                }
            }
        }
    }
}