package self.nesl.newshub.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.newshub.R
import self.nesl.newshub.thenIfNotNull
import self.nesl.newshub.ui.theme.*


@Composable
fun AppMaxWidthItem(
    title: String,
    icon: Painter? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .thenIfNotNull(onClick) { clickable(onClick = it) }
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.space_16)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(icon != null) {
            Icon(
                painter = icon,
                contentDescription = title,
            )
        }
        Text(
            text = title,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.space_16)),
            style = NewshubTheme.typography.bodyMedium,
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.space_1))
    )
}

@Preview
@Composable
private fun PreviewAppMaxWidthItem() {
    PreviewTheme {
        AppMaxWidthItem(
            title = "title",
            icon = null,
            onClick = { },
        )
    }
}