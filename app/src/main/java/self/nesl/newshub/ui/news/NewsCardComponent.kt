package self.nesl.newshub.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import self.nesl.newshub.*
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.AppDisabledAlpha
import self.nesl.newshub.ui.theme.AppLink
import self.nesl.newshub.ui.theme.NewshubTheme


@Composable
fun CardHeadPosterBlock(poster: String?) {
    CardHeadTextBlock(poster?.takeIf { it.isNotBlank() }.toString())
}

@Composable
fun CardHeadTimeBlock(timestamp: Long?) {
    CardHeadTextBlock(timestamp?.toHumanTime() ?: "time")
}

@Composable
fun CardHeadRepliesBlock(replies: Int?, onShowMoreClick: (() -> Unit)? = null) {
    if (onShowMoreClick == null || replies.isZeroOrNull()) {
        CardHeadTextBlock(
            text = (replies ?: 0).toString(),
        )
    } else {
        CardHeadTextBlock(
            text = (replies ?: 0).toString(),
            color = AppLink,
            onClick = onShowMoreClick,
        )
    }
}

@Composable
fun CardHeadBlock(
    modifier: Modifier = Modifier,
    compose: @Composable () -> Unit = { },
) {
    Box(
        modifier = modifier
            .padding(end = dimensionResource(id = R.dimen.space_4))
    ) {
        compose()
    }
}

@Composable
fun CardHeadTextBlock(
    text: String = "",
    color: Color = LocalContentColor.current.copy(alpha = AppDisabledAlpha),
    onClick: (() -> Unit)? = null,
) {
    CardHeadBlock(
        modifier = Modifier
            .doIf(onClick != null) { clickable { onClick!!.invoke() } },
    ) {
        Text(
            text = text,
            style = NewshubTheme.typography.bodySmall,
            color = color,
        )
    }
}