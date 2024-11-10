package ch.ascendise.chipless.views.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay
import ch.ascendise.chipless.widgets.Player

@Composable
fun GameView(
    players: Array<PlayerModel> = emptyArray(),
) {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape

    Box(modifier = Modifier.fillMaxSize()) {
        PokerTable(modifier = Modifier.fillMaxSize())
        for(player in players) {
            Player(
                name = player.name,
                balance = player.balance
            )
        }
    }
}