package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class EndOfRoundRuleTests {

    @Test
    fun `should proceed to next round if every player has bet the same amount`() {
        //Arrange
        val sut = EndOfRoundRule()
        val players = listOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            round = PokerEngine.Round(
                startingPlayer = players[0],
                players = Rotation(players.map { PokerEngine.Seat(it, 10) }),
                name = PokerEngine.BettingRounds.Hole
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        //Act
        val newState = sut.act(state)
        //Assert
        assertEquals(PokerEngine.BettingRounds.Flop, newState?.round?.name, "Game is still in old round")
    }

    @Test
    fun `should do nothing when it is still the 'current' round`() {
        //Arrange
        val sut = EndOfRoundRule()
        val players = listOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            round = PokerEngine.Round(
                startingPlayer = players[0],
                players = Rotation(players.map { PokerEngine.Seat(it, 0) }),
                name = PokerEngine.BettingRounds.Hole
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        with(state) {
            round.players.items
                .first { it != round.activePlayer }
                .bets = 50
        }
        //Act
        val newState = sut.act(state)
        //Assert
        assertNull(newState, "Rule was applied!")
    }
}