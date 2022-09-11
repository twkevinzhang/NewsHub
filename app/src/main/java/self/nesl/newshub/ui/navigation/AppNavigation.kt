package self.nesl.newshub.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class AppNavigation(navController: NavHostController) {
    val navigateWithPop: (NavItems) -> Unit = {
        navController.navigate(it.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
