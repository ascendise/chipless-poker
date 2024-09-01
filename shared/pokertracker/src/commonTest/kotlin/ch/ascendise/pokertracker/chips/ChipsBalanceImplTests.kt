package ch.ascendise.pokertracker.chips

import ch.ascendise.pokertracker.mocks.ChipCheater
import kotlin.test.*

internal class ChipsBalanceImplTests {

    @Test
    fun `should remove chips from balance and add them to transaction object`() {
        //Arrange
        val chipsBalanceImpl = ChipsBalanceImpl(100, null)
        //Act
        chipsBalanceImpl.withdraw(40).use { chips ->
            //Assert
            assertEquals(40, chips.amount)
            assertEquals(60, chipsBalanceImpl.amount)
            val chipCheater = ChipCheater()
            chipCheater.deposit(chips)
        }
    }

    @Test
    fun `should throw exception when trying to initialize negative balance`() {
        //Arrange
        //Act
        val initBalance = { ChipsBalanceImpl(-100, null) }
        //Assert
        assertFailsWith<IllegalArgumentException> { initBalance() }
    }

    @Test
    fun `should throw exception when trying to remove negative balance`() {
        //Arrange
        val chipsBalanceImpl = ChipsBalanceImpl(100, null)
        //Act
        val removeNegativeChips = { chipsBalanceImpl.withdraw(-40) }
        //Assert
        assertFailsWith<IllegalArgumentException> { removeNegativeChips() }
        assertEquals(100, chipsBalanceImpl.amount, "Corrupted chips balance")
    }

    @Test
    fun `should throw exception when trying to overdraw balance`() {
        //Arrange
        val chipsBalanceImpl = ChipsBalanceImpl(10, null)
        //Act
        val removeTooManyChips = { chipsBalanceImpl.withdraw(100) }
        //Assert
        assertFailsWith<BalanceOverdrawnException> { removeTooManyChips() }
        assertEquals(10, chipsBalanceImpl.amount, "Corrupted chips balance")
    }

    @Test
    fun `should move chips from source to target balance`() {
        //Arrange
        val sourceBalance = ChipsBalanceImpl(100, null)
        val targetBalance = ChipsBalanceImpl(0, null)
        //Act
        sourceBalance.withdraw(40).use { chips ->
            targetBalance.deposit(chips)
            //Assert
            assertEquals(60, sourceBalance.amount)
            assertEquals(40, targetBalance.amount)
        }
    }

    @Test
    fun `should remove amount from chips object after transaction to avoid duplicating chips`() {
        //Arrange
        val sourceBalance = ChipsBalanceImpl(100, null)
        val targetBalance = ChipsBalanceImpl(0, null)
        //Act
        sourceBalance.withdraw(40).use { chips ->
            targetBalance.deposit(chips)
            targetBalance.deposit(chips)
            targetBalance.deposit(chips)
            //Assert
            assertEquals(60, sourceBalance.amount)
            assertNotEquals(120, targetBalance.amount, "Chips were duplicated!")
            assertEquals(40, targetBalance.amount)
            assertEquals(0, chips.amount, "Chips still kept amount")
        }
    }

    @Test
    fun `should throw when chips are destroyed`() {
        //Arrange
        val chipsBalanceImpl = ChipsBalanceImpl(100, null)
        //Act
        val destroyWithoutTransfer = { chipsBalanceImpl.withdraw(40).use { _ -> /*Do nothing*/ }}
        //Assert
        assertFailsWith<ChipsDestroyedException> { destroyWithoutTransfer() }
    }
}