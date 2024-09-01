package ch.ascendise.pokertracker.engine.mocks

import ch.ascendise.pokertracker.engine.PokerEngine

/**
 * Always returns the same state
 *
 */
internal class StubCommand(private val state: PokerEngine.State): PokerEngine.Command {

    override fun execute(state: PokerEngine.State?): PokerEngine.State
        = this.state

}