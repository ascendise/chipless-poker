package ch.ascendise.pokertracker.chips

class ChipsDestroyedException(chips: ChipsBalance.Chips)
    : Exception("Chips were destroyed! amount: ${chips.amount}")