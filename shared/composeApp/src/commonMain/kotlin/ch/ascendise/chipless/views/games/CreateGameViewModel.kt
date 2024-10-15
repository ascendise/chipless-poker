package ch.ascendise.chipless.views.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel


class CreateGameViewModel : ViewModel() {
    val state by mutableStateOf(State())

    fun createNewPlayer() {
        val newPlayer = CreatePlayerModel("Player #${state.players.count() + 1}")
        state.players.add(newPlayer)
        if(state.dealer.value == null)
           state.dealer.value = newPlayer
        state.playersLeft.value--
    }

    fun deletePlayer(createPlayerModel: CreatePlayerModel) {
        state.players.remove(createPlayerModel)
        state.playersLeft.value++
    }

    fun changeDealer(playerIndex: Int) {
        state.dealer.value = state.players[playerIndex]
    }

    data class State(
        var players: SnapshotStateList<CreatePlayerModel> = mutableStateListOf(),
        var playersLeft: MutableState<Int> = mutableStateOf(2),
        var dealer: MutableState<CreatePlayerModel?> = mutableStateOf(null),
        var balancePerPlayer: MutableState<Int> = mutableStateOf(100)
    )
}

