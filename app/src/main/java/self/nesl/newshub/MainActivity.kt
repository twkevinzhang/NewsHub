package self.nesl.newshub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.util.Consumer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import self.nesl.newshub.ui.bindAppScreen
import self.nesl.newshub.ui.navigation.NewsNavItems

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            bindAppScreen(navController = navController)
            bindThreadListener(navController)
        }
    }

    @Composable
    private fun bindThreadListener(navController: NavHostController) {
        DisposableEffect(Unit) {
            intent.dataString?.let { uri ->
                navController.navigate(NewsNavItems.Thread.route.plus("/${uri.encode()}"))
            }
            val listener = Consumer<Intent> {
                it.dataString?.let { uri ->
                    navController.navigate(NewsNavItems.Thread.route.plus("/${uri.encode()}"))
                }
            }
            addOnNewIntentListener(listener)
            onDispose { removeOnNewIntentListener(listener) }
        }
    }
}
