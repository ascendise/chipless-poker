package ch.ascendise.pokertracker.mocks

import ch.ascendise.pokertracker.ChipsBalance
import ch.ascendise.pokertracker.Player

/**
 * Used to add or remove chips for testing purposes without disrupting the flow of the universe
 *
 */
class ChipCheater : ChipsBalance(0, null) {

    /**
     * Moves chips into endless, hungry void, destroying them forever
     *
     * @param amount
     */
    override fun deposit(amount: Chips) {
        amount.depositTo(this)
    }

    /**
     * Summons any amount of chips into the world
     *
     * @param amount
     * @return
     */
    override fun withdraw(amount: Int): Chips
        = CheaterChips(amount, owner)

    class CheaterChips(amount: Int, owner: Player?) : Chips(amount, owner) { }
}