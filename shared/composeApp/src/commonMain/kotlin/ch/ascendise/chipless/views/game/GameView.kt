package ch.ascendise.chipless.views.game

import androidx.compose.runtime.Composable
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.getDisplay

@Composable
fun GameView() {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape
}