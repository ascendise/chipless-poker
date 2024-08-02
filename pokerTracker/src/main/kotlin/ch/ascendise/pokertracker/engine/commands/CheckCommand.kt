package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine

class CheckCommand : PlayerCommand() {

    override fun onExecute(state: PokerEngine.State): PokerEngine.State {
        assertValidMove(state)
        rotateActivePlayer(state)
        return state
    }

    private fun rotateActivePlayer(state: PokerEngine.State) {
        with(state.round) {
            activePlayer = players.next()
        }
    }

    private fun assertValidMove(state: PokerEngine.State) {
        if((state.round.activePlayer.bets) < state.round.highestBet) {
            throw InvalidStateException("Player is behind highest bet")
        }
    }

}