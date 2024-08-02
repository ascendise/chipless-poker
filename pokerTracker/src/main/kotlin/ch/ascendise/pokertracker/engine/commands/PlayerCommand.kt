package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.GameNotInitalizedException
import ch.ascendise.pokertracker.engine.PokerEngine

abstract class PlayerCommand : PokerEngine.Command {

    final override fun execute(state: PokerEngine.State?): PokerEngine.State {
        if(state == null)
            throw GameNotInitalizedException()
        return onExecute(state)
    }

    protected abstract fun onExecute(state: PokerEngine.State): PokerEngine.State

}