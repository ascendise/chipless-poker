package ch.ascendise.pokertracker.engine.rules

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.engine.Rule
import ch.ascendise.pokertracker.rotation.Rotation

/**
 * This rule applies at the end of a betting round
 */
internal class EndOfRoundRule: Rule<PokerEngine.State>() {

    override fun evaluate(state: PokerEngine.State?): Boolean {
        if(state == null || state.round.actionCount < state.players.count())
            return false
        return state.round.activePlayer.bets == state.round.highestBet
    }

    override fun apply(state: PokerEngine.State?): PokerEngine.State {
        state!!.round = state.nextRound()
        return state
    }

    private fun PokerEngine.State.nextRound(): PokerEngine.Round {
        val currentRound = round.name.ordinal
        val rounds = BettingRounds.entries
        val nextRoundName = rounds[currentRound.inc() % rounds.count()]
        round.players.items.forEach { it.bets = 0 }
        return PokerEngine.Round(round.players, getNextStartingPlayer(), nextRoundName)
    }

    private fun PokerEngine.State.getNextStartingPlayer(): Player {
        if (players.count() == 2)
            return bigBlind
        return getPlayerOrNext(smallBlind)
    }

    private fun PokerEngine.State.getPlayerOrNext(player: Player): Player {
        if(round.players.items.any { it.player == player })
            return player
        val rotation = Rotation(players.toMutableList(), players.indexOf(smallBlind))
        var next = rotation.next()
        while(!round.players.items.any { it.player == next }) {
            next = rotation.next()
        }
        return next
    }
}

