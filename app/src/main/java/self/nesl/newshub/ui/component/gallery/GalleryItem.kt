package self.nesl.newshub.ui.component.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import self.nesl.newshub.R
import self.nesl.newshub.ui.theme.NewshubTheme

import java.lang.Float.min
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AsyncGallery(
    urls: List<String>,
    startIndex: Int = 0,
    onDismissRequest: () -> Unit = { }
) {
    val pagerState = rememberPagerState(initialPage = startIndex)

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                count = urls.size,
                state = pagerState,
            ) { page ->
                var loaded by remember { mutableStateOf(false) }
                ZoomableBox(
                    loaded = loaded,
                ) {
                    SubcomposeAsyncImage(
                        model = urls[page],
                        contentDescription = null,
                        loading = { CircularProgressIndicator() },
                        onSuccess = { loaded = true }
                    )
                }
            }
        }
    }

}

@Composable
fun ZoomableBox(
    loaded: Boolean = false,
    minScale: Float? = null,
    maxScale: Float = 5f,
    contentCompose: @Composable () -> Unit
) {
    var screen by remember { mutableStateOf(IntSize.Zero) }
    var content by remember { mutableStateOf(IntSize.Zero) }
    var scale by remember { mutableStateOf(1f) }
    var initScale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // 當 loaded, screen, content 有變化時，就會執行這個 block，用以初始化 zoom 值
    // 初始化 zoom 值：將圖片縮放至 fill max width or height
    LaunchedEffect(loaded, screen, content) {
        if (loaded &&
            screen.width > 0 && content.width > 0 &&
            screen.height > 0 && content.height > 0
        ) {
            val scaleWithFull = min(
                screen.width.dp / content.width.dp,
                screen.height.dp / content.height.dp
            )
            initScale = scaleWithFull
            scale = scaleWithFull
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged { screen = it }
        .background(MaterialTheme.colorScheme.background)
        .pointerInput(Unit) {
            detectTransformGesturesWithUnconsumed { event ->
                val _minScale = minScale ?: initScale
                val centroid = event.calculateCentroid()
                val pan = event.calculatePan()
                val gestureZoom = event.calculateZoom()
                val rotation = event.calculateRotation()

                scale = (scale * gestureZoom).coerceIn(_minScale, maxScale)
                val zoomedX = content.width * scale
                offsetX = if (zoomedX < size.width) {
                    0f
                } else {
                    val (negative, positive) = unAbs(zoomedX.limitIn(size.width))
                    (offsetX + pan.x).coerceIn(negative, positive)
                }
                val zoomedY = content.height * scale
                offsetY = if (zoomedY < size.height) {
                    0f
                } else {
                    val (negative, positive) = unAbs(zoomedY.limitIn(size.height))
                    (offsetY + pan.y).coerceIn(negative, positive)
                }
                if (scale > _minScale) {
                    event.changes.consumeAll()
                }
            }
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .onSizeChanged { content = it }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX,
                translationY = offsetY
            )
        ) {
            contentCompose()
        }
    }
}

private suspend fun PointerInputScope.detectTransformGesturesWithUnconsumed(callback: (event: PointerEvent) -> Unit) {
    forEachGesture {
        awaitPointerEventScope {
            awaitFirstDown()
            do {
                val event = awaitPointerEvent()
                callback(event)
            } while (event.changes.any { it.pressed })
        }
    }
}

private fun Float.limitIn(screen: Int): Float {
    return screen.minus(this).div(2)
}

private fun unAbs(f: Float): Pair<Float, Float> {
    return Pair(-f.absoluteValue, f.absoluteValue)
}

private fun List<PointerInputChange>.consumeAll() {
    fastForEach {
        if (it.positionChanged()) {
            it.consume()
        }
    }
}

@Preview
@Composable
fun PreviewZoomableBox() {
    NewshubTheme {
        ZoomableBox(true) {
            Image(painter = painterResource(id = R.drawable.yushan_banner_495x1200), contentDescription = null)
        }
    }
}