package ch.ascendise.pokertracker

import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import kotlin.random.Random

class Player(
    startingChips: Int,
    val name: String = "Player #${Random.nextInt(from = 1000, until = 9999)}"
) {

    var balance = ChipsBalanceImpl(startingChips, this)
        private set

    /**
     * Remove chips from balance to bet on table
     *
     * @param amount
     * @return
     */
    fun bet(amount: Int): ChipsBalance.Chips {
        return balance.withdraw(amount)
    }
}