package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.engine.PokerEngine

class FoldCommand : PlayerCommand()  {

    override fun onExecute(state: PokerEngine.State): PokerEngine.State {
        with(state) {
            round.players.remove { it.player == activePlayer }
        }
        return state
    }

}