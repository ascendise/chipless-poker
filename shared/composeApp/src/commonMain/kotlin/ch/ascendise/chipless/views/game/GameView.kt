package ch.ascendise.chipless.views.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay
import ch.ascendise.chipless.positioning.Rectangle
import ch.ascendise.chipless.widgets.Player

@Composable
fun GameView(
    players: Array<PlayerModel> = emptyArray(),
) {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape
    val rectangle = Rectangle(500.0,  300.0)
    val points = rectangle.splitEvenly(players.count())
    Box(modifier = Modifier.fillMaxSize()) {
        PokerTable(modifier = Modifier.fillMaxSize())
        for(i in 0..<players.count()) {
            val player = players[i]
            val point = points[i]
            Player(
                name = player.name,
                balance = player.balance,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = point.x.dp, y = point.y.dp)
                    .size(64.dp)
            )
        }
    }
}