package self.nesl.newshub.ui.component.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun LazyGallery(
    state: LazyListState,
    startIndex: Int = 0,
    onDismissRequest: () -> Unit = { },
    item: @Composable LazyListScope.(Int) -> Unit,
) {
    TODO("not implemented yet")
}
