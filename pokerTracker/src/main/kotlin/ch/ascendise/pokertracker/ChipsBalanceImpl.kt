package ch.ascendise.pokertracker

class ChipsBalanceImpl(startingAmount: Int, owner: Player?) : ChipsBalance(startingAmount, owner) {

    /**
     * Adds chips to the balance
     *
     * @param amount
     */
    override fun deposit(amount: Chips) {
        amount.depositTo(this)
    }

    /**
     * Removes an amount of chips and moves them into a Chips object,
     * to be added to another balance
     *
     * @param amount
     * @return
     */
    override fun withdraw(amount: Int): Chips {
        return Bet(amount, owner)
    }

    private inner class Bet(amount: Int, owner: Player?) : Chips(amount, owner) {
        init {
            if(amount <= 0)
                throw IllegalArgumentException("amount cannot be less than 1")
            val newBalance = this@ChipsBalanceImpl.amount - amount
            if(newBalance < 0) {
                throw BalanceOverdrawnException(this@ChipsBalanceImpl.amount, amount)
            }
            this@ChipsBalanceImpl.amount -= amount
        }
    }


}