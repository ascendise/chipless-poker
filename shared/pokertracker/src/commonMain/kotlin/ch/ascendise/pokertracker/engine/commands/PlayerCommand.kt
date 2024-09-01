package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.engine.GameNotInitializedException
import ch.ascendise.pokertracker.engine.PokerEngine

internal abstract class PlayerCommand : PokerEngine.Command {

    final override fun execute(state: PokerEngine.State?): PokerEngine.State {
        if(state == null)
            throw GameNotInitializedException()
        return onExecute(state)
    }

    protected abstract fun onExecute(state: PokerEngine.State): PokerEngine.State

}