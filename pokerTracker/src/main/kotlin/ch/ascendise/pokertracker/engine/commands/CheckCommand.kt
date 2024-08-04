package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine

internal class CheckCommand : PlayerCommand() {

    override fun onExecute(state: PokerEngine.State): PokerEngine.State {
        assertValidMove(state)
        return state
    }

    private fun assertValidMove(state: PokerEngine.State) {
        if((state.round.activePlayer.bets) < state.round.highestBet) {
            throw InvalidStateException("Player is behind highest bet")
        }
    }

}