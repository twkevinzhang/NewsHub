package dev.zlong.newshub.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.zlong.newshub.R
import dev.zlong.newshub.ui.theme.AppDarkBlue
import dev.zlong.newshub.ui.theme.AppWhite
import dev.zlong.newshub.ui.theme.NewshubTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsHubTopBar(
    title: String = "",
    onMenuPressed: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    SmallTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
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
    )
}

@Preview
@Composable
private fun PreviewNewsHubTopBar() {
    NewshubTheme {
        NewsHubTopBar("NewsHub")
    }
}

@Preview
@Composable
private fun PreviewNewsHubTopBarWithBack() {
    NewshubTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onBackPressed = { },
        )
    }
}

@Preview
@Composable
private fun PreviewNewsHubTopBarWithMenu() {
    NewshubTheme {
        NewsHubTopBar(
            title = "NewsHub",
            onMenuPressed = { },
        )
    }
}