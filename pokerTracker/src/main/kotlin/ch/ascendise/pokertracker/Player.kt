package ch.ascendise.pokertracker

class Player(startingChips: Int) {

    var chips = ChipsBalance(startingChips, this)
        private set

    fun bet(amount: Int): ChipsBalance.Chips {
        return chips.remove(amount)
    }
}