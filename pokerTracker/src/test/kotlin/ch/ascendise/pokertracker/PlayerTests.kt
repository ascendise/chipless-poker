package ch.ascendise.pokertracker

import ch.ascendise.pokertracker.chips.BalanceOverdrawnException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PlayerTests {

    @Test
    fun `should remove chips from player when creating a bet`() {
        //Arrange
        val player = Player(100)
        //Act
        val bet = player.bet(40)
        //Assert
        assertEquals(40, bet.amount, "Bet has not the correct amount of chips")
        assertEquals(60, player.balance.amount, "Player chips were not removed")
    }

    @Test
    fun `should throw exception when creating a bet that overdraws player balance`() {
        //Arrange
        val player = Player(10)
        //Act
        val invalidBet = { player.bet(1000) }
        //Assert
        assertThrows<BalanceOverdrawnException> { invalidBet() }
        assertEquals(10, player.balance.amount, "Player balance corrupted")
    }

    @Test
    fun `should throw exception when creating a negative bet`() {
        //Arrange
        val player = Player(100)
        //Act
        val invalidBet = { player.bet(-100) }
        //Assert
        assertThrows<IllegalArgumentException> { invalidBet() }
        assertEquals(100, player.balance.amount, "Player balance corrupted")
    }
}