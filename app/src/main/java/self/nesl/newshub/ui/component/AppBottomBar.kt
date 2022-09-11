package self.nesl.newshub.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.newshub.ui.navigation.*
import self.nesl.newshub.R

@Composable
fun AppBottomBar(
    navItems: List<NavItems>,
    onNavItemClick: (NavItems) -> Unit = { },
    selectedItem : NavItems? = null,
) {
    BottomAppBar(
        tonalElevation = dimensionResource(id = R.dimen.space_0),
    ) {
        navItems.forEach { item ->
            NavigationRailItem (
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route
                    )
                },
                selected = selectedItem == item,
                onClick = { onNavItemClick(item) },
                label = {
                    Text(text = stringResource(id = item.resourceId))
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAppBottomBar() {
    AppBottomBar(
        navItems = bottomNavItems(),
    )
}