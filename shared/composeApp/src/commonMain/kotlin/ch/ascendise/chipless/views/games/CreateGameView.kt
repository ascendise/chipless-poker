package ch.ascendise.chipless.views.games

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.components.MenuButton
import ch.ascendise.chipless.components.SlideRemovable
import ch.ascendise.chipless.widgets.BalanceTopBar
import ch.ascendise.chipless.widgets.PlayerInfoWidget
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.add_1_players
import chiplesspoker.shared.composeapp.generated.resources.add_2_players
import chiplesspoker.shared.composeapp.generated.resources.add_player_button
import chiplesspoker.shared.composeapp.generated.resources.create_game
import org.jetbrains.compose.resources.stringResource
import sh.calvin.reorderable.ReorderableColumn

@Composable
internal fun CreateGameView(
    onCreateNewPlayer: () -> Unit = {},
    onDeletePlayer: (CreatePlayerModel) -> Unit = {},
    onChangeDealer: (Int) -> Unit = {},
    onStartGame: () -> Unit = {},
    state: CreateGameViewModel.State = CreateGameViewModel.State()) {

    Column(modifier = Modifier.fillMaxWidth()) {
        BalanceTopBar(
            balance = state.balancePerPlayer.value,
            onBalanceChange = { state.balancePerPlayer.value = it }
        )
        ReorderableColumn(
            list = state.players.toList(),
            onSettle = { from, to ->
                state.players.apply {
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
                        onPlayerEdit = { state.players[index] = it },
                        isDealer = state.dealer.value == player,
                        setAsDealer = { onChangeDealer(index) },
                    )
                }
            }
        }
        MenuButton(
            onClick = onCreateNewPlayer,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(Res.string.add_player_button))
        }
        val createGameCaption = when(state.playersLeft.value) {
            2 -> stringResource(Res.string.add_2_players)
            1 -> stringResource(Res.string.add_1_players)
            else -> stringResource(Res.string.create_game)
        }
        MenuButton(
            onClick = onStartGame ,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.playersLeft.value <= 0) {
            Text(text = createGameCaption)
        }
    }
}



