package ch.ascendise.pokertracker.chips

import ch.ascendise.pokertracker.InvalidPlayException

/**
 * Thrown when a bet would cause the chip balance to go below 0
 *
 * @property currentAmount amount the player had before he made the bet
 * @property attemptedBet amount of the bet
 */
class BalanceOverdrawnException(val currentAmount: Int, val attemptedBet: Int)
    : InvalidPlayException("Bet overdraws balance. " +
        "Attempted to bet $attemptedBet with only $currentAmount left")
