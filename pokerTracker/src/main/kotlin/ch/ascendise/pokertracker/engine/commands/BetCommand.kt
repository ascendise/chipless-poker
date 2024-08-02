package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.GameNotInitalizedException
import ch.ascendise.pokertracker.WrongPlayerException
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine

/**
 * Player command to place a bet
 *
 * @property chips
 */
class BetCommand(val chips: ChipsBalance.Chips) : PlayerCommand() {

    override fun onExecute(state: PokerEngine.State): PokerEngine.State {
        assertValidMove(state)
        with(state) {
            placeBet()
            rotateActivePlayer()
        }
        return state
    }

    private fun PokerEngine.State.placeBet() {
        round.activePlayer.bets += chips.amount
        pot.deposit(chips)
    }

    private fun PokerEngine.State.rotateActivePlayer() {
        round.activePlayer = round.players.next()
    }

    private fun assertValidMove(state: PokerEngine.State) {
        if (chips.owner != state.activePlayer)
            throw WrongPlayerException()
    }
}