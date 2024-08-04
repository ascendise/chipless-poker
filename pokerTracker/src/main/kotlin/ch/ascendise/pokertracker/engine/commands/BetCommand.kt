package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.engine.WrongPlayerException
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.PokerEngine

/**
 * Player command to place a bet
 *
 * @property chips
 */
internal class BetCommand(val chips: ChipsBalance.Chips) : PlayerCommand() {

    override fun onExecute(state: PokerEngine.State): PokerEngine.State {
        assertValidMove(state)
        with(state) {
            placeBet()
        }
        return state
    }

    private fun PokerEngine.State.placeBet() {
        round.activePlayer.bets += chips.amount
        pot.deposit(chips)
    }

    private fun assertValidMove(state: PokerEngine.State) {
        if (chips.owner != state.activePlayer)
            throw WrongPlayerException()
    }
}