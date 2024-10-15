package ch.ascendise.chipless.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun SlideRemovable(
    modifier: Modifier = Modifier,
    orientation: Orientation,
    toDeleteOffset: Float = 512f,
    onDelete: () -> Unit = {},
    content: @Composable () -> Unit) {

    fun toIntOffset(offset: Float) : IntOffset = when(orientation) {
        Orientation.Horizontal -> IntOffset(offset.roundToInt(), 0)
        Orientation.Vertical -> IntOffset(0, offset.roundToInt())
    }

    var offset by remember { mutableStateOf(0f) }
    var alphaValue by remember { mutableStateOf(1f) }
    var color by remember { mutableStateOf(Color.Transparent) }

    fun resetSlide() {
        offset = 0f
        alphaValue = 1f
        color = Color.Transparent
    }

    Surface(modifier = modifier
        .offset { toIntOffset(offset) }
        .background(color)
        .alpha(alphaValue)
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = { resetSlide() }
            ) { _ , dragAmount ->
                offset += dragAmount
                if (offset.absoluteValue >= toDeleteOffset) {
                    onDelete()
                    resetSlide()
                }
                alphaValue = 1 - offset.absoluteValue / toDeleteOffset
                color = when (alphaValue) {
                    1f -> Color.Transparent
                    else -> Color.Red
                }
            }
        },
        color = Color.Transparent
    ) {
        content()
    }
}