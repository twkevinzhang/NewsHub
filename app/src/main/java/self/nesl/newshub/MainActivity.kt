package self.nesl.newshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import self.nesl.newshub.ui.bindAppScreen
import self.nesl.newshub.ui.topic.TopicViewModel
import self.nesl.newshub.ui.theme.NewshubTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            bindAppScreen()
        }
    }
}
