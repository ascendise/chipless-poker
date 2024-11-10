package ch.ascendise.chipless.views.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.Chip
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay

@Composable
fun GameView() {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape

    Box(modifier = Modifier.fillMaxSize()) {
        PokerTable(modifier = Modifier.fillMaxSize())
        Chip(5000)
    }
}