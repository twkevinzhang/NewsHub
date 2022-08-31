package self.nesl.newshub.ui.theme

import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

private val colorPalette = lightColors(
    primary = AppDarkNavy,
    secondary = AppTeal,
    surface = AppDarkBlue,
    background = AppDarkBlue,
    error = AppDarkBlue,
    onPrimary = AppWhite,
    onSecondary = AppWhite,
    onBackground = AppWhite,
    onSurface = AppWhite,
    onError = AppWhite,
)

object NewshubTheme {
    val colors
        @Composable
        @ReadOnlyComposable
        get() = colorPalette

    val typography
        @Composable
        @ReadOnlyComposable
        get() = Typography

    val shapes
        @Composable
        @ReadOnlyComposable
        get() = Shapes
}

@Composable
fun NewshubTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = NewshubTheme.colors,
        typography = NewshubTheme.typography,
        shapes = NewshubTheme.shapes,
        content = content
    )
}