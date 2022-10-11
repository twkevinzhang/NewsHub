package self.nesl.newshub.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class AppNavigation(navController: NavHostController) {
    val navigateWithPop: (NavItems) -> Unit = {
        val route = when (it) {
            is TopicNavItems -> "topic/${it.route}"
            else -> it.route
        }
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
