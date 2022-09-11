package self.nesl.newshub.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import self.nesl.newshub.R
import self.nesl.newshub.ui.navigation.NavItems
import self.nesl.newshub.ui.navigation.drawerNavItems
import self.nesl.newshub.ui.navigation.topicNavItems
import self.nesl.newshub.ui.theme.*

@Composable
fun Drawer(
    topNavItems: List<NavItems>,
    onTopNavItemClick: (NavItems) -> Unit = { },
    bottomNavItems: List<NavItems>,
    onBottomNavItemClick: (NavItems) -> Unit = { },
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DrawerHeader(name = "NewsHub")
            topNavItems.forEach {
                AppMaxWidthItem(
                    title = stringResource(id = it.resourceId),
                    icon = painterResource(id = it.icon),
                    onClick = { onTopNavItemClick(it) }
                )
                Surface(
                    tonalElevation = 1.dp,
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.space_1))
                    )
                }
            }
        }

        Surface(
            tonalElevation = 1.dp,
        ) {
            Column {
                bottomNavItems.forEach {
                    AppMaxWidthItem(
                        title = stringResource(id = it.resourceId),
                        icon = painterResource(id = it.icon),
                        onClick = { onBottomNavItemClick(it) }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.space_1))
                            .background(color = NewshubTheme.colors.background)
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerHeader(
    name: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.space_16))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_100)))
        Text(text = name, style = NewshubTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_2)))
    }
}

@Preview
@Composable
private fun PreviewDrawer() {
    PreviewTheme {
        Drawer(
            topNavItems = topicNavItems(),
            bottomNavItems = drawerNavItems(),
        )
    }
}
