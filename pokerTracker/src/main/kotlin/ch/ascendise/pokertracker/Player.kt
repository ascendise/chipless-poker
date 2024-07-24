package ch.ascendise.pokertracker

import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl

class Player(startingChips: Int) {

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