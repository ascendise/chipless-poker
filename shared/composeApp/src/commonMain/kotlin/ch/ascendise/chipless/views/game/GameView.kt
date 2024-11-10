package ch.ascendise.chipless.views.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.Chip
import ch.ascendise.chipless.components.Player
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay

@Composable
fun GameView() {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape

    Box(modifier = Modifier.fillMaxSize()) {
        PokerTable(modifier = Modifier.fillMaxSize())
        Chip(5000)
        Player("Player1", modifier = Modifier.size(128.dp).align(Alignment.CenterStart))
        Player("Player2", modifier = Modifier.size(128.dp).align(Alignment.CenterEnd))
        Player("Player3", modifier = Modifier.size(128.dp).align(Alignment.Center))
        Player("Player4", modifier = Modifier.size(128.dp).align(Alignment.BottomStart))
    }
}