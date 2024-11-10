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


/**
 * ViewModel for creating a new game.
 * Handles configuration of the game as well as any validation
 */
class CreateGameViewModel : ViewModel() {
    val state by mutableStateOf(State())

    /**
     * Adds a new player with a default name to the game
     * If no dealer is defined yet, the created player will automatically be assigned as the dealer.
     */
    fun createNewPlayer() {
        val newPlayer = CreatePlayerModel("Player #${state.players.count() + 1}")
        state.players.add(newPlayer)
        if(state.dealer.value == null)
           state.dealer.value = 0
        state.playersLeft.value--
    }

    /**
     * Removes the provided player from the game
     *
     * @param createPlayerModel reference to the player to be removed
     */
    fun deletePlayer(createPlayerModel: CreatePlayerModel) {
        state.players.remove(createPlayerModel)
        state.playersLeft.value++
    }

    /**
     * Reassigns dealer to a new player
     *
     * @param playerIndex
     */
    fun changeDealer(playerIndex: Int) {
        assertInRange(playerIndex, state.players)
        state.dealer.value = playerIndex
    }

    private fun <T> assertInRange(index: Int?, coll: Collection<T>) {
        if(index == null || index < 0 || index >= coll.count())
            throw IndexOutOfBoundsException("$index out of range of collection. (Count is ${coll.count()})")
    }

    /**
     * Creates a new game from the current configuration
     *
     * At least 2 players are required to create a new game.
     *
     * @return new game
     * @exception InvalidGameException
     */
    fun createGame(): Table {
        if(state.playersLeft.value > 0)
            throw InvalidGameException("At least 2 players required")
        val players = getPlayers()
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

    /**
     * State of the game configuration
     *
     * @property players list of players participating in the game
     * @property playersLeft count of players you need to add before being able to start the game
     * @property dealer starting dealer of the game. The index points to a player in players
     * @property balancePerPlayer the starting chips for every player in the game
     */
    data class State(
        val players: SnapshotStateList<CreatePlayerModel> = mutableStateListOf(),
        val playersLeft: MutableState<Int> = mutableStateOf(2),
        val dealer: MutableState<Int?> = mutableStateOf(null),
        val balancePerPlayer: MutableState<Int> = mutableStateOf(100),
    )
}

