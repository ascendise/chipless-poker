package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.WrongPlayerException
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine

/**
 * Player command to place a bet
 *
 * @property chips
 */
class BetCommand(val chips: ChipsBalance.Chips) : PokerEngine.Command {

    override fun execute(state: PokerEngine.State?): PokerEngine.State {
        assertValidMove(state)
        with(state!!) {
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

    private fun assertValidMove(state: PokerEngine.State?) {
        if (state == null)
            throw InvalidStateException("Can't bet on uninitialized round")
        if (chips.owner != state.activePlayer)
            throw WrongPlayerException()
    }
}