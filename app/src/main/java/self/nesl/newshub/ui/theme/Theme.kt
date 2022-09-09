package self.nesl.newshub.ui.theme

import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.tooling.preview.Preview
import self.nesl.newshub.ui.component.Drawer
import self.nesl.newshub.ui.navigation.drawerNavItems
import self.nesl.newshub.ui.navigation.topicNavItems

private val ColorPalette = darkColors(
)

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
        colors = NewshubTheme.colors,
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