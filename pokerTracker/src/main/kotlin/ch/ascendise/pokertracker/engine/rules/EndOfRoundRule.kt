package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.engine.Rule

/**
 * This rule applies at the end of a betting round
 */
class EndOfRoundRule: Rule<PokerEngine.State>() {

    override fun evaluate(state: PokerEngine.State?): Boolean {
        if(state == null)
            return false
        return state.round.activePlayer.bets == state.round.highestBet
    }

    override fun apply(state: PokerEngine.State?): PokerEngine.State {
        state!!.round = state.nextRound()
        return state
    }

    private fun PokerEngine.State.nextRound(): PokerEngine.Round {
        val currentRound = round.name.ordinal
        val rounds = PokerEngine.BettingRounds.entries
        val nextRoundName = rounds[currentRound.inc() % rounds.count()]
        round.players.items.forEach { it.bets = 0 }
        return PokerEngine.Round(round.players, getNextStartingPlayer(), nextRoundName)
    }

    private fun PokerEngine.State.getNextStartingPlayer(): Player {
        if (players.count() == 2)
            return bigBlind
        return smallBlind
    }
}

