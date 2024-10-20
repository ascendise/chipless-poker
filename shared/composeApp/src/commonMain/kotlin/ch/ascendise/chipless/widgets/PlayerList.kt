package ch.ascendise.chipless.widgets

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.components.SlideRemovable
import ch.ascendise.chipless.views.gamemanagement.CreatePlayerModel
import sh.calvin.reorderable.ReorderableColumn

@Composable
fun PlayerList(
    players: MutableList<CreatePlayerModel>,
    dealer: Int?,
    onDeletePlayer: (CreatePlayerModel) -> Unit = {},
    onChangeDealer: (Int) -> Unit = {},
) {
    ReorderableColumn(
        list = players,
        onSettle = { from, to ->
           players.apply {
               add(to, removeAt(from))
           }
        },
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { index, player, _ ->
        key(player.name){
            SlideRemovable(
                modifier = Modifier.draggableHandle(),
                orientation = Orientation.Horizontal,
                onDelete = { onDeletePlayer(player) }) {
                PlayerInfoWidget(
                    player = player,
                    onPlayerEdit = { players[index] = it },
                    isDealer = index == dealer,
                    setAsDealer = { onChangeDealer(index) },
                )
            }
        }
    }
}