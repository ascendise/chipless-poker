package ch.ascendise.pokertracker

class ChipsBalance(private var startingAmount: Int, private val owner: Player?) {

    var balance: Int
        private set

    init {
        balance = startingAmount
        if(balance <= 0)
            balance = 1
    }

    fun add(amount: Chips) {

    }

    fun remove(amount: Int): Chips {
        return Bet(amount, owner)
    }

    interface Chips {
        val amount: Int
        val owner: Player?
    }

    private inner class Bet(override val amount: Int, override val owner: Player?) : Chips {
        init {
            val newBalance = balance - amount
            if(newBalance < 0) {
                throw BalanceOverdrawnException(balance, amount)
            }
            balance -= amount
        }
    }

}