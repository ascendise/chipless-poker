package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.engine.Rule

/**
 * This rule applies when only one player is left and automatically selected as the winner
 *
 */
internal class RewardWinnerRule : Rule<PokerEngine.State>() {

    override fun evaluate(state: PokerEngine.State?): Boolean
        = state != null && state.round.players.items.count() == 1



    override fun apply(state: PokerEngine.State?): PokerEngine.State? {
        val winner = state!!.round.players.items.single().player
        state.pot.withdraw(state.pot.amount).use { chips ->
            winner.balance.deposit(chips)
        }
        return null
    }

}
