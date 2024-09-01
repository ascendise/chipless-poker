package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import kotlin.test.*

class EndOfRoundRuleTests {
    @Test
    fun `should do nothing when not every player had the chance to bet yet`() {
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
            blinds = Blinds(0, 0),
            round = PokerEngine.Round(
                startingPlayer = players[0],
                players = Rotation(players.map { PokerEngine.Seat(it, 0) }.toMutableList()),
                name = BettingRounds.Hole,
                actionCount = 1
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        //Act
        val result = sut.act(state)
        //Assert
        assertFalse(result.applied, "Rule was applied!")
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
                players = Rotation(players.map { PokerEngine.Seat(it, 0) }.toMutableList()),
                name = BettingRounds.Hole,
                actionCount = 1
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        with(state) {
            round.players.items
                .first { it != round.activePlayer }
                .bets = 50
        }
        //Act
        val result = sut.act(state)
        //Assert
        assertFalse(result.applied, "Rule was applied!")
    }

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
                players = Rotation(players.map { PokerEngine.Seat(it, 10) }.toMutableList()),
                name = BettingRounds.Hole,
                actionCount = 3
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        //Act
        val result = sut.act(state)
        //Assert
        assertTrue(result.applied, "Rule was not applied!")
        assertEquals(BettingRounds.Flop, result.state?.round?.name, "Game is still in old round")
    }

    @Test
    fun `should select next active player if small blind has folded`() {
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
                players = Rotation(players.map { PokerEngine.Seat(it, 10) }.toMutableList()),
                name = BettingRounds.Hole,
                actionCount = 3
            ),
            pot = ChipsBalanceImpl(30, null)
        )
        state.round.players.remove { it.player == state.smallBlind }
        //Act
        val result = sut.act(state)
        //Assert
        assertTrue(result.applied, "Rule was not applied!")
        assertEquals(state.bigBlind, result.state!!.activePlayer)
    }
}