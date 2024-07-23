package ch.ascendise.pokertracker

class BalanceOverdrawnException(val currentAmount: Int, val attemptedBet: Int)
    : InvalidPlayException("Bet overdraws balance. " +
        "Attempted to bet $attemptedBet with only $currentAmount left"){
}