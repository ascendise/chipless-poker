package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TableTests {

    @Test
    fun `should throw when trying to create table with less than two players`() {
        //Arrange
        val players = mutableListOf(
            Player(100)
        )
        //Act
        val createIllegalTable = { Table(players, players.first()) }
        //Assert
        assertThrows<IllegalArgumentException> { createIllegalTable() }
    }

    @Test
    fun `should throw when trying to create table with foreign player`() {
        //Arrange
        val foreigner = Player(Int.MAX_VALUE)
        val players = mutableListOf(
            Player(100)
        )
        //Act
        val createIllegalTable = { Table(players, foreigner) }
        //Assert
        assertThrows<IllegalArgumentException> { createIllegalTable() }
    }

    @ParameterizedTest
    @MethodSource("startingPlayerTestCases")
    fun `should define correct active player when creating new table`(expectedActiveIndex: Int, playerCount: Int, dealerIndex: Int) {
        //Arrange
        val players = mutableListOf<Player>()
        for (i in 1..playerCount)
            players.add(Player(100))

        //Act
        val table = Table(players, players[dealerIndex])
        //Assert
        assertEquals(expectedActiveIndex, players.indexOf(table.activePlayer), "Wrong player defined as active")
    }

    private fun startingPlayerTestCases()
            = Stream.of(
                //expectedActiveIndex, playerCount; dealerIndex
                Arguments.of(0, 4, 1),
                Arguments.of(0, 3, 0),
                Arguments.of(0, 2, 0)
            )

    @Test
    fun `should advance active player after bet`() {
        //Arrange
        val players = arrayListOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0])
        //Act
        val bet = table.activePlayer.bet(20)
        table.bet(bet)
        //Assert
        assertEquals(0, players.indexOf(table.activePlayer), "Wrong player index defined as active")
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
        val table = Table(players, players[0])
        val potSnapshot = table.currentPot
        //Act
        val bet = table.activePlayer.bet(20)
        table.bet(bet)
        //Assert
        assertEquals(20 + potSnapshot, table.currentPot)
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
        val blinds = Table.Blinds(5, 10)
        val dealer = players[0]
        val table = Table(players, dealer, blinds)
        //Act
        val potSnapshot = table.currentPot
        val illegalMove = {
            dealer.bet(20).use { bet ->
                table.bet(bet)
            }
        }
        //Assert
        assertThrows<WrongPlayerException> { illegalMove() }
        assertEquals(potSnapshot, table.currentPot, "Table balance corrupted")
    }
}