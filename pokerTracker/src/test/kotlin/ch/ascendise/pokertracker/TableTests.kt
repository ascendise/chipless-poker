package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TableTests {

    @Test
    fun `Creating table should define correct active player`() {
        //Arrange
        val players = arrayListOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100)
        )
        //Act
        val table = Table(players, players[1])
        //Assert
        assertEquals(0, players.indexOf(table.activePlayer), "Wrong player defined as active")
    }

    @Test
    fun `Should move blinds into pot at start of game`() {
        //Arrange
        val players = arrayListOf(
            Player(100),
            Player(100),
            Player(100),
        )
        val blinds = Table.Blinds(5, 10)
        //Act
        val table = Table(players, players[0], blinds)
        val smallBlind = players[1]
        val bigBlind = players[2]
        //Assert
        assertEquals(95, smallBlind.balance.amount)
        assertEquals(90, bigBlind.balance.amount)
        assertEquals(15, table.currentPot)
    }

    @Test
    fun `Should advance active player after bet`() {
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
    fun `Should move chips into pot on bet`() {
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
    fun `Should throw when non-active player is trying to bet`() {
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