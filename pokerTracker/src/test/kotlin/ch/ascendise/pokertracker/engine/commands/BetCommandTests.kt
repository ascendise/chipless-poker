package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.WrongPlayerException
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BetCommandTests {

    @Test
    fun `should advance active player after bet`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100)
        )
        val seats = players.stream().map { PokerEngine.Seat(it) }.toList()
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            pot = ChipsBalanceImpl(0, null),
            round = PokerEngine.Round(Rotation(seats), players[3], PokerEngine.BettingRounds.Hole)
        )
        //Act
        state.activePlayer.bet(20).use { chips ->
            val bet = BetCommand(chips)
            val newState = bet.execute(state)
            //Assert
            assertEquals(0, players.indexOf(newState.activePlayer), "Wrong player index defined as active")
        }
    }

    @Test
    fun `should move chips into pot on bet`() {
        //Arrange
        val players = arrayListOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100)
        )
        val seats = players.stream().map { PokerEngine.Seat(it) }.toList()
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            pot = ChipsBalanceImpl(0, null),
            round = PokerEngine.Round(Rotation(seats), players[3], PokerEngine.BettingRounds.Hole)
        )
        //Act
        state.activePlayer.bet(20).use { chips ->
            val bet = BetCommand(chips)
            val newState = bet.execute(state)
            //Assert
            assertEquals(20, newState.pot.amount)
        }
    }

    @Test
    fun `should throw when non-active player is trying to bet`() {
        //Arrange
        val players = arrayListOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100),
        )
        val seats = players.stream().map { PokerEngine.Seat(it) }.toList()
        //Act
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[1],
            bigBlind = players[2],
            blinds = PokerEngine.Blinds(5, 10),
            pot = ChipsBalanceImpl(0, null),
            round = PokerEngine.Round(Rotation(seats), players[3], PokerEngine.BettingRounds.Hole)
        )
        var newState: PokerEngine.State? = null
        val illegalMove = {
            state.dealer.bet(20).use { chips ->
                val bet = BetCommand(chips)
                newState = bet.execute(state)
            }
        }
        //Assert
        assertThrows<WrongPlayerException> { illegalMove() }
        assertNull(newState, "New state created despite illegal play")
    }
}