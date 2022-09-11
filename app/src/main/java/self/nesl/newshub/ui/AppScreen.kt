package self.nesl.newshub.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import self.nesl.newshub.ui.component.AppBottomBar
import self.nesl.newshub.ui.component.Drawer
import self.nesl.newshub.ui.component.NewsHubTopBar
import self.nesl.newshub.ui.topic.TopicViewModel
import self.nesl.newshub.ui.topic.bindTopicScreen
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.ui.theme.AppDarkBlue

@Composable
fun bindAppScreen(
    topicViewModel: TopicViewModel,
    navController: NavHostController,
) {
    val defaultNav = DrawerNavItems.Home
    var currentNavItem by remember { mutableStateOf<NavItems>(defaultNav) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    val closeDrawer = {
        scope.launch {
            drawerState.close()
        }
    }

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerElevation = 0.dp,
        drawerContent = {
            Drawer(
                topNavItems = topicNavItems(),
                onTopNavItemClick = {
                    closeDrawer()
                    navController.navigate(it.route)
                },
                bottomNavItems = drawerNavItems(),
            )
        }
    ) {
        Scaffold(
            topBar = { NewsHubTopBar(onMenuPressed = { openDrawer() },
            title = stringResource(id = currentNavItem.resourceId)) },
            bottomBar = { AppBottomBar(bottomNavItems()) },
        ) {
            NavHost(
                navController = navController,
                startDestination = defaultNav.route,
            ) {
                composable(DrawerNavItems.Home.route) {
                    currentNavItem = DrawerNavItems.Home
                }

                composable(TopicNavItems.Square.route) {
                    currentNavItem = TopicNavItems.Square
                    topicViewModel.topic(TopicNavItems.Square)
                    bindTopicScreen(
                        topicViewModel = topicViewModel,
                        navController = navController,
                    )
                }

                composable(TopicNavItems.Movie.route) {
                    currentNavItem = TopicNavItems.Movie
                }
            }
        }
    }
}