package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CheckCommandTests {

    @Test
    fun `should throw if player is behind on bet`() {
        //Arrange
        val players = listOf(
            Player(90),
            Player(100)
        )
        val rotation = Rotation(players.map { PokerEngine.Seat(it, 0) }.toMutableList(), 1)
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[0],
            bigBlind = players[1],
            pot = ChipsBalanceImpl(10, null),
            blinds = Blinds(0, 0),
            round = PokerEngine.Round(
                players = rotation,
                name = BettingRounds.Hole,
                startingPlayer =  players[0],
                activePlayer = rotation.items[1]
            )
        )
        state.round.getSeat(players[0]).bets = 10 //first player bet 10 chips
        //Act
        val command = CheckCommand()
        val illegalAction = { command.execute(state) }
        //Assert
        assertThrows<InvalidStateException> { illegalAction() }
    }
}