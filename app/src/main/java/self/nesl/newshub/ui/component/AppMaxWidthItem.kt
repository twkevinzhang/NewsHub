package self.nesl.newshub.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.AppDarkNavy
import self.nesl.newshub.ui.theme.AppDarkSpaceNavy
import self.nesl.newshub.ui.theme.AppWhite
import self.nesl.newshub.ui.theme.NewshubTheme


@Composable
fun AppMaxWidthItem(
    title: String,
    icon: Painter? = null,
    onClick: (() -> Unit)? = null,
) {
    val modifier = onClick?.let {
        Modifier.clickable(onClick = { onClick() })
    } ?: Modifier
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.space_16)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(icon != null) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = AppWhite,
            )
        }
        Text(
            text = title,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.space_16)),
            fontSize = 16.sp,
            color = AppWhite,
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.space_1))
            .background(color = NewshubTheme.colors.primary)
    )
}

@Preview
@Composable
private fun PreviewAppMaxWidthItem() {
    NewshubTheme() {
        AppMaxWidthItem(
            title = "title",
            icon = null,
            onClick = { },
        )
    }
}