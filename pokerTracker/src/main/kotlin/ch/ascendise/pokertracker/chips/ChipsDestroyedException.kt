package ch.ascendise.pokertracker.chips

import java.lang.Exception

class ChipsDestroyedException(chips: ChipsBalance.Chips)
    : Exception("Chips were destroyed! amount: ${chips.amount}")