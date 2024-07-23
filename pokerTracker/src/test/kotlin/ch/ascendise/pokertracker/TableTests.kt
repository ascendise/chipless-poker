package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TableTests {

    @Test
    fun `creating table should define correct active player`() {
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
        assertEquals(players[0], table.activePlayer)
    }
}