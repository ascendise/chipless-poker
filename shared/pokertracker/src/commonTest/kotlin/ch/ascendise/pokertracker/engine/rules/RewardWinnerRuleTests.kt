package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import kotlin.test.*

class RewardWinnerRuleTests {

    @Test
    fun `should select winner and end rule if only one player is left`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val winner = players[0]
        val rotation = Rotation(players.map { PokerEngine.Seat(it) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            pot = ChipsBalanceImpl(10, null),
            blinds = Blinds(1, 2),
            round = PokerEngine.Round(
                players = rotation,
                name = BettingRounds.Hole,
                startingPlayer = players[0]
            )
        )
        state.round.players.remove { it.player != winner }
        val rule = RewardWinnerRule()
        //Act
        val result = rule.act(state)
        //Assert
        assertTrue(result.applied, "Rule was not applied!")
        assertEquals(winner.balance.amount, 110)
    }

    @Test
    fun `should not apply rule if game is still going`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val rotation = Rotation(players.map { PokerEngine.Seat(it) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            pot = ChipsBalanceImpl(10, null),
            blinds = Blinds(1, 2),
            round = PokerEngine.Round(
                players = rotation,
                name = BettingRounds.Hole,
                startingPlayer = players[0]
            )
        )
        val rule = RewardWinnerRule()
        //Act
        val result = rule.act(state)
        //Assert
        assertFalse(result.applied, "Rule was applied!")
    }

    @Test
    fun `should not apply rule if game is not running`() {
        //Arrange
        val rule = RewardWinnerRule()
        //Act
        val result = rule.act(null)
        //Assert
        assertFalse(result.applied, "Rule was applied!")
    }
}