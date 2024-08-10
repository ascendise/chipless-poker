package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class TableTests {

    @Test
    fun `should initialize table correctly`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100),
            Player(100),

        )
        //Act
        val table = Table(players, players[1], Blinds(1, 2))
        //Assert
        with(table.gameInfo!!) {
            assertEquals(players[0], activePlayer, "Wrong active player")
            assertEquals(players[1], dealer, "Wrong dealer")
            assertEquals(players[2], smallBlind, "Wrong small blind")
            assertEquals(players[3], bigBlind, "Wrong big blind")
            assertEquals(BettingRounds.Hole, round, "Wrong round")
            assertEquals(Blinds(1, 2), blinds, "Wrong blinds set")
            assertEquals(3, pot)
        }
    }

    @Test
    fun `should bet through one round`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0], Blinds(1, 2))
        //Act
        table.call()
        table.check()
        //Assert
        with(table.gameInfo!!) {
            assertEquals(players[0], dealer, "Wrong dealer")
            assertEquals(players[0], smallBlind, "Wrong small blind")
            assertEquals(players[1], bigBlind, "Wrong big blind")
            assertEquals(players[1], activePlayer, "Wrong active player")
            assertEquals(BettingRounds.Flop, round, "Wrong round")
            assertEquals(Blinds(1, 2), blinds, "Wrong blinds set")
            assertEquals(4, pot)
        }
    }

    @Test
    fun `should bet through one game`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0], Blinds(1, 2))
        //Act
        table.call()
        table.check()
            //Flop
        table.raise(table.gameInfo!!.activePlayer.bet(4))
        table.call()
            //Turn
        table.check()
        table.check()
            //River
        //Assert
        with(table.gameInfo!!) {
            assertEquals(players[1], activePlayer, "Wrong active player")
            assertEquals(BettingRounds.River, round, "Wrong round")
            assertEquals(Blinds(1, 2), blinds, "Wrong blinds set")
            assertEquals(12, pot)
        }
    }

    @Test
    fun `should skip player after fold`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0], Blinds(1, 2))
        //Act
        table.call()
        table.call()
        table.check()
            //Flop
        table.fold()
        table.check()
        table.check()
            //Turn
        //Assert
        with(table.gameInfo!!) {
            assertEquals(players[2], activePlayer, "Wrong active player")
            assertEquals(BettingRounds.Turn, round, "Wrong round")
            assertEquals(Blinds(1, 2), blinds, "Wrong blinds set")
        }
    }

    @Test
    fun `should automatically move pot to winner if all other players folded`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0], Blinds(1, 2))
        //Act
            //Starting Player is Dealer
        table.raise(table.gameInfo!!.activePlayer.bet(4)) //Dealer 96 ; Pot 7
        table.call() //Small Blind 96 ; Pot 10
        table.fold() //Big Blind 98
        //Flop
            //Starting Player is Small Blind
        table.raise(table.gameInfo!!.activePlayer.bet(4)) //Small Blind 92 ; Pot 14
        table.fold() //Dealer 96 ;
        //Assert
        assertNull(table.gameInfo, "Game is still going!")
        assertEquals(106, players[1].balance.amount)
    }

    @Test
    fun `should select and reward winner if winner is inconclusive at the end of the game`() {
        //Arrange
        val players = mutableListOf(
            Player(100),
            Player(100)
        )
        val table = Table(players, players[0], Blinds(1, 2))
        //Act
            //Hole
        table.call()
        table.check()
            //Flop
        table.check()
        table.check()
            //Turn
        table.check()
        table.check()
            //River
        table.check()
        table.check()
            //Winner Selection
        table.selectWinner(players[0])
        //Assert
        assertNull(table.gameInfo)
        assertEquals(102, players[0].balance.amount)
        assertEquals(98, players[1].balance.amount)
    }
}