package ch.ascendise.chipless.views.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.Chip
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay
import ch.ascendise.chipless.widgets.Player

@Composable
fun GameView() {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape

    Box(modifier = Modifier.fillMaxSize()) {
        PokerTable(modifier = Modifier.fillMaxSize())
        Player("Niguel", 100000, Modifier.align(Alignment.BottomCenter).size(64.dp))
    }
}