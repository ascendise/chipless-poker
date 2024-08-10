package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.engine.GameNotInitializedException
import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.swing.plaf.nimbus.State

class SelectWinnerCommandTests {

    @Test
    fun `should throw exception if player is not in game`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100)
        )
        val foreignPlayer = Player(5)
        val command = SelectWinnerCommand(foreignPlayer)
        val rotation = Rotation(players.map { PokerEngine.Seat(it) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[0],
            bigBlind = players[1],
            pot = ChipsBalanceImpl(50, null),
            round = PokerEngine.Round(
                name = BettingRounds.Flop,
                players = rotation,
                activePlayer = rotation.items[1],
                startingPlayer = players[1]
            )
        )
        //Act
        val illegalCommand = { command.execute(state) }
        //Assert
        assertThrows<IllegalArgumentException> { illegalCommand() }
    }

    @Test
    fun `should throw exception if no game is running`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100)
        )
        val command = SelectWinnerCommand(players[0])
        //Act
        val illegalCommand = { command.execute(null) }
        //Assert
        assertThrows<GameNotInitializedException> { illegalCommand() }
    }

    @Test
    fun `should throw exception if winner is not to be selected yet`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100)
        )
        val command = SelectWinnerCommand(players[0])
        val rotation = Rotation(players.map { PokerEngine.Seat(it) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[0],
            bigBlind = players[1],
            pot = ChipsBalanceImpl(50, null),
            round = PokerEngine.Round(
                name = BettingRounds.Flop,
                players = rotation,
                activePlayer = rotation.items[1],
                startingPlayer = players[1]
            )
        )
        //Act
        val illegalCommand = { command.execute(state) }
        //Assert
        assertThrows<InvalidStateException> { illegalCommand() }
    }

    @Test
    fun `should move reward to winner and close game`() {
        //Arrange
        val players = listOf(
            Player(100),
            Player(100)
        )
        val command = SelectWinnerCommand(players[0])
        val rotation = Rotation(players.map { PokerEngine.Seat(it) }.toMutableList())
        val state = PokerEngine.State(
            players = players,
            dealer = players[0],
            smallBlind = players[0],
            bigBlind = players[1],
            pot = ChipsBalanceImpl(50, null),
            round = PokerEngine.Round(
                name = BettingRounds.Reward,
                players = rotation,
                activePlayer = rotation.items[1],
                startingPlayer = players[1]
            )
        )
        //Act
        val result = command.execute(state)
        //Assert
        assertNull(result, "Game is still going!")
        assertEquals(150, players[0].balance.amount)
    }
}