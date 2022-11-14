package self.nesl.newshub.ui.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.R
import self.nesl.newshub.thenIfNotNull
import self.nesl.newshub.ui.theme.PreviewTheme
import kotlin.math.roundToInt

@Composable
fun AppBottomBar(
    navItems: List<NavItems>,
    onNavItemClick: (NavItems) -> Unit = { },
    selectedItem : NavItems? = null,
    scrollBehavior: BottomAppBarScrollBehavior? = null
) {
    BottomAppBar(
        tonalElevation = dimensionResource(id = R.dimen.space_0),
        modifier = Modifier
            .thenIfNotNull(scrollBehavior) {
                val offsetY = -it.state.offsetY.value.roundToInt()
                this.height(it.state.height.dp - offsetY.dp)
                    .offset { IntOffset(x = 0, y = offsetY) }
            }
    ) {
        navItems.forEach { item ->
            NavigationBarItem (
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route
                    )
                },
                selected = selectedItem == item,
                onClick = { onNavItemClick(item) },
                label = { NavItemText(item = item) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAppBottomBar() {
    PreviewTheme {
        AppBottomBar(
            navItems = bottomNavItems(),
        )
    }
}

interface BottomAppBarState {
    var height: Float
    val offsetY: MutableState<Float>
}

interface BottomAppBarScrollBehavior {
    val nestedScrollConnection: NestedScrollConnection
    val state: BottomAppBarState
}

@Composable
fun rememberBottomAppBarState(): BottomAppBarState {
    return object : BottomAppBarState {
        override var height = 80f
        override val offsetY = remember { mutableStateOf(0f) }
    }
}

@Composable
fun BottomAppBarDefaults.enterAlwaysScrollBehavior(
    state: BottomAppBarState = rememberBottomAppBarState(),
): BottomAppBarScrollBehavior {
    return object : BottomAppBarScrollBehavior {
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