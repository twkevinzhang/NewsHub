package self.nesl.newshub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.tooling.preview.Preview

object NewshubTheme {
    val colors
        @Composable
        @ReadOnlyComposable
        get() = ColorPalette

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
        colorScheme = NewshubTheme.colors,
        typography = NewshubTheme.typography,
        shapes = NewshubTheme.shapes,
        content = content
    )
}

@Preview
@Composable
fun PreviewTheme(
    content: @Composable () -> Unit =  {},
) {
    NewshubTheme() {
        Surface {
            content()
        }
    }
}