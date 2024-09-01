package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import kotlin.test.*

class FoldCommandTests {

    @Test
    fun `should remove player from current round`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100)
        )
        val rotation = Rotation(players.map { PokerEngine.Seat(it, 0) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[0],
            bigBlind = players[1],
            pot = ChipsBalanceImpl(0, null),
            blinds = Blinds(0, 0),
            round = PokerEngine.Round(
                players = rotation,
                name = BettingRounds.Hole,
                startingPlayer =  players[0],
                activePlayer = rotation.items[0]
            )
        )
        val fold = FoldCommand()
        //Act
        val newState = fold.execute(state)
        //Assert
        assertFalse(newState.round.players.items.any { it.player == players[0] },
            "Player is still active in round")
    }


}