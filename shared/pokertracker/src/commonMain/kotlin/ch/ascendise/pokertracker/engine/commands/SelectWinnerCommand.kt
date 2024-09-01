package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.engine.GameNotInitializedException
import ch.ascendise.pokertracker.engine.InvalidStateException
import ch.ascendise.pokertracker.engine.PokerEngine

internal class SelectWinnerCommand(val player: Player) : PokerEngine.Command{

    override fun execute(state: PokerEngine.State?): PokerEngine.State? {
        if(state == null)
            throw GameNotInitializedException()
        if(!state.round.players.items.any { it.player == player })
            throw IllegalArgumentException("Player is not in current round (anymore)")
        if(state.round.name != BettingRounds.Reward)
            throw InvalidStateException("Game is still going!")
        state.pot.withdraw(state.pot.amount).use { chips ->
            player.balance.deposit(chips)
        }
        return null
    }

}