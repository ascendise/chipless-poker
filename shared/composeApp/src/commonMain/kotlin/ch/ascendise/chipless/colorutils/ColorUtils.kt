package ch.ascendise.chipless.colorutils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.random(): Color {
    val red = Random.nextInt(255)
    val green = Random.nextInt(255)
    val blue = Random.nextInt(255)
    return Color(red, green, blue, 255)
}