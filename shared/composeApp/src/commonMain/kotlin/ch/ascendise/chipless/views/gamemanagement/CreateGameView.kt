package ch.ascendise.chipless.views.gamemanagement

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.components.MenuButton
import ch.ascendise.chipless.components.SlideRemovable
import ch.ascendise.chipless.widgets.BalanceTopBar
import ch.ascendise.chipless.widgets.PlayerInfoWidget
import ch.ascendise.chipless.widgets.PlayerList
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
        PlayerList(
            players =  state.players.toMutableList(),
            dealer = state.dealer.value,
            onDeletePlayer = onDeletePlayer,
            onChangeDealer = onChangeDealer
        )
        MenuButton(
            onClick = onCreateNewPlayer,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(Res.string.add_player_button))
        }
        MenuButton(
            onClick = onStartGame ,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.playersLeft.value <= 0) {
            when(state.playersLeft.value) {
                2 ->  Text(text = stringResource(Res.string.add_2_players), color = Color.Gray)
                1 ->  Text(text = stringResource(Res.string.add_1_players), color = Color.Gray)
                else ->  Text(text = stringResource(Res.string.create_game))
            }
        }
    }
}



