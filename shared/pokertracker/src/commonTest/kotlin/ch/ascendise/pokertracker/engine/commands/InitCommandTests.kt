package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import kotlin.test.*

class InitCommandTests {

    private fun startingPlayerTest(expectedActiveIndex: Int, playerCount: Int, dealerIndex: Int) {
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

    @Test
    fun `should define correct starting player with 4 players`() {
        startingPlayerTest(0, 4, 1)
    }

    @Test
    fun `should define correct starting player with 3 players`() {
        startingPlayerTest(0, 3, 0)
    }

    @Test
    fun `should define correct starting player with 2 players`() {
        startingPlayerTest(0, 2, 0)
    }

    @Test
    fun `should define correct blinds player`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), Blinds(5, 10))
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
        assertFailsWith<IllegalArgumentException> { createIllegalCommand() }
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
        assertFailsWith<IllegalArgumentException> { createIllegalCommand() }
    }

    @Test
    fun `should not throw when initializing engine without blinds`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), Blinds(0, 0))
        initCommand.execute(null)
    }

    @Test
    fun `should pay blinds automatically`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        //Act
        val initCommand = InitCommand(players, players.first(), Blinds(5, 10))
        val state = initCommand.execute(null)
        //Assert
        assertEquals(5, state.round.getSeat(state.smallBlind).bets)
        assertEquals(10, state.round.getSeat(state.bigBlind).bets)
        assertEquals(15, state.pot.amount)
    }

}