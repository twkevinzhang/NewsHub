package self.nesl.newshub.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import self.nesl.newshub.ui.theme.NewshubTheme
import self.nesl.newshub.R
import self.nesl.newshub.thenIfNotNull
import self.nesl.newshub.ui.theme.AppDarkBlue
import self.nesl.newshub.ui.theme.AppWhite
import self.nesl.newshub.ui.theme.PreviewTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHubTopBar(
    title: String = "",
    onMenuPressed: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    scrollBehavior: AppBarScrollBehavior? = null
) {
    SmallTopAppBar(
        title = {
            Text(
                text = title,
                style = NewshubTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            if (onBackPressed != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            } else if (onMenuPressed != null) {
                IconButton(onClick = onMenuPressed) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Menu",
                    )
                }
            }
        },
        modifier = Modifier
            .thenIfNotNull(scrollBehavior) {
                val offsetY = it.state.offsetY.value.roundToInt()
                this.height(it.state.height.dp - offsetY.dp)
                    .offset { IntOffset(x = 0, y = offsetY) }
            },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewNewsHubTopBar() {
    PreviewTheme {
        NewsHubTopBar("NewsHub")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewNewsHubTopBarWithBack() {
    PreviewTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onBackPressed = { },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewNewsHubTopBarWithMenu() {
    PreviewTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onMenuPressed = { },
        )
    }
}

@Composable
fun rememberTopAppBarState(): AppBarState {
    return object : AppBarState {
        override var height = 40f
        override val offsetY = remember { mutableStateOf(0f) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults.enterAlwaysScrollBehavior(
    state: AppBarState = rememberBottomAppBarState(),
): AppBarScrollBehavior {
    return object : AppBarScrollBehavior {
        override val nestedScrollConnection = object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = state.offsetY.value + delta
                state.offsetY.value = newOffset.coerceIn(-state.height, 0f)

                return Offset.Zero
            }
        }
        override val state = state
    }
}