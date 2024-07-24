package ch.ascendise.pokertracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PlayerTests {

    @Test
    fun `Creating a bet should remove chips from player`() {
        //Arrange
        val player = Player(100)
        //Act
        val bet = player.bet(40)
        //Assert
        assertEquals(40, bet.amount, "Bet has not the correct amount of chips")
        assertEquals(60, player.balance.amount, "Player chips were not removed")
    }

    @Test
    fun `Creating invalid bet should throw exception`() {
        //Arrange
        val player = Player(10)
        //Act
        val invalidBet = { player.bet(1000) }
        //Assert
        assertThrows<BalanceOverdrawnException> { invalidBet() }
        assertEquals(10, player.balance.amount, "Player balance corrupted")
    }

    @Test
    fun `Creating negative bet should throw exception`() {
        //Arrange
        val player = Player(100)
        //Act
        val invalidBet = { player.bet(-100) }
        //Assert
        assertThrows<IllegalArgumentException> { invalidBet() }
        assertEquals(100, player.balance.amount, "Player balance corrupted")
    }
}