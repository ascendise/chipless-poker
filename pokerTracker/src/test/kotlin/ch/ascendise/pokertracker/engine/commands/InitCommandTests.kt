package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.engine.PokerEngine
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InitCommandTests {

    @ParameterizedTest
    @MethodSource("startingPlayerTestCases")
    fun `should define correct active player when initializing round`(expectedActiveIndex: Int, playerCount: Int, dealerIndex: Int) {
        //Arrange
        val players = mutableListOf<Player>()
        for (i in 1..playerCount)
            players.add(Player(100))
        //Act
        val command = InitCommand(players, players[dealerIndex])
        val state = command.execute(null)
        //Assert
        assertEquals(expectedActiveIndex, players.indexOf(state.activePlayer), "Wrong player defined as active")
    }

    private fun startingPlayerTestCases()
            = Stream.of(
        //expectedActiveIndex, playerCount; dealerIndex
        Arguments.of(0, 4, 1), //4+ players; Player after Big Blind starts
        Arguments.of(0, 3, 0), //3 players; Dealer starts
        Arguments.of(0, 2, 0)  //2 players; Dealer/small Blind starts
    )

    @Test
    fun `should define correct blinds player`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), PokerEngine.Blinds(5, 10))
        val state = initCommand.execute(null)
        //Assert
        assertSame(players[1], state.smallBlind)
        assertSame(players[2], state.bigBlind)
    }

    @Test
    fun `should throw when trying to create InitCommand with less than two players`() {
        //Arrange
        val players = mutableListOf(
            Player(100)
        )
        //Act
        val createIllegalCommand = { InitCommand(players, players.first()) }
        //Assert
        assertThrows<IllegalArgumentException> { createIllegalCommand() }
    }

    @Test
    fun `should throw when trying to create InitCommand with foreign dealer`() {
        //Arrange
        val foreigner = Player(Int.MAX_VALUE)
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        //Act
        val createIllegalCommand = { InitCommand(players, foreigner) }
        //Assert
        assertThrows<IllegalArgumentException> { createIllegalCommand() }
    }

    @Test
    fun `should handle initializing engine without blinds`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), PokerEngine.Blinds(0, 0))
        val createEngineWithoutBlinds = { initCommand.execute(null) }
        //Assert
        assertDoesNotThrow { createEngineWithoutBlinds() }
    }

    @Test
    fun `should pay blinds automatically`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), PokerEngine.Blinds(5, 10))
        val state = initCommand.execute(null)
        //Assert
        assertEquals(5, state.round.getSeat(state.smallBlind).bets)
        assertEquals(10, state.round.getSeat(state.bigBlind).bets)
        assertEquals(15, state.pot.amount)
    }

}