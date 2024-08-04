package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
        with(table.gameInfo) {
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
        with(table.gameInfo) {
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
        table.raise(table.gameInfo.activePlayer.bet(4))
        table.call()
            //Turn
        table.check()
        table.check()
            //River
        //Assert
        with(table.gameInfo) {
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
        with(table.gameInfo) {
            assertEquals(players[2], activePlayer, "Wrong active player")
            assertEquals(BettingRounds.Turn, round, "Wrong round")
            assertEquals(Blinds(1, 2), blinds, "Wrong blinds set")
        }
    }
}