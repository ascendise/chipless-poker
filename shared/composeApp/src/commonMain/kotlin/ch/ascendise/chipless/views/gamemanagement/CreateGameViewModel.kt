package ch.ascendise.chipless.views.gamemanagement

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.Table


class CreateGameViewModel : ViewModel() {
    val state by mutableStateOf(State())

    fun createNewPlayer() {
        val newPlayer = CreatePlayerModel("Player #${state.players.count() + 1}")
        state.players.add(newPlayer)
        if(state.dealer.value == null)
           state.dealer.value = 0
        state.playersLeft.value--
    }

    fun deletePlayer(createPlayerModel: CreatePlayerModel) {
        state.players.remove(createPlayerModel)
        state.playersLeft.value++
    }

    fun changeDealer(playerIndex: Int) {
        assertInRange(playerIndex, state.players)
        state.dealer.value = playerIndex
    }

    private fun <T> assertInRange(index: Int?, coll: Collection<T>) {
        if(index == null || index < 0 || index >= coll.count())
            throw IndexOutOfBoundsException("$index out of range of collection. (Count is ${coll.count()})")
    }

    fun createGame(): Table {
        val players = getPlayers()
        if(players.count() < 2)
            throw InvalidGameException("At least 2 players required")
        return Table(
            players = players,
            startingDealer = players[0] ,
            blinds = Blinds(1, 2)
        )
    }

    private fun getPlayers(): MutableList<Player> {
        return state.players
            .map { toPlayer(it) }
            .toMutableList()
    }

    private fun toPlayer(player: CreatePlayerModel): Player
        = Player(state.balancePerPlayer.value, player.name)

    data class State(
        val players: SnapshotStateList<CreatePlayerModel> = mutableStateListOf(),
        val playersLeft: MutableState<Int> = mutableStateOf(2),
        val dealer: MutableState<Int?> = mutableStateOf(null),
        val balancePerPlayer: MutableState<Int> = mutableStateOf(100)
    )
}

