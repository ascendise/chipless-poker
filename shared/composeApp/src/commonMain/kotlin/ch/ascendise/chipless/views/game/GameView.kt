package ch.ascendise.chipless.views.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.components.PokerTable
import ch.ascendise.chipless.getDisplay
import ch.ascendise.chipless.positioning.Point
import ch.ascendise.chipless.positioning.Rectangle
import ch.ascendise.chipless.widgets.Player
import kotlin.math.cos

@Composable
fun GameView(
    players: Array<PlayerModel> = emptyArray(),
) {
    val display = getDisplay()
    display.orientation = Display.Orientation.Landscape
    var tableSize by remember { mutableStateOf(IntSize.Zero) }
    val points = calculateSeatCoordinates(tableSize, 200, players.count(), LocalDensity.current)
    Box(modifier = Modifier.fillMaxSize()
        .onGloballyPositioned { tableSize = it.size }) {
        PokerTable(modifier = Modifier.fillMaxSize())
        if(points.isNotEmpty()) {
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
}

internal fun calculateSeatCoordinates(
    tableSize: IntSize,
    offset: Int,
    playerCount: Int,
    density: Density): Array<Point> {

    if(tableSize == IntSize.Zero)
        return emptyArray()
    val offsetTableSize = with(tableSize) { IntSize(width - offset, height - offset)}
    val tableSizeDp = Rectangle(
        with(density) { offsetTableSize.width.toDp().value.toDouble() },
        with(density) { offsetTableSize.height.toDp().value.toDouble() },
    )
    val rectangle = Rectangle(
        tableSizeDp.width,
        tableSizeDp.height
    )
    return rectangle.splitEvenly(playerCount)
}
