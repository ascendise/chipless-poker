package ch.ascendise.pokertracker

import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.engine.commands.BetCommand
import ch.ascendise.pokertracker.engine.commands.CheckCommand
import ch.ascendise.pokertracker.engine.commands.FoldCommand
import ch.ascendise.pokertracker.engine.commands.InitCommand
import ch.ascendise.pokertracker.engine.commands.SelectWinnerCommand

/**
 * Main interface of the poker game
 *
 * @constructor
 * Creates a new game of poker with the first round initialized
 *
 * @param players
 * @param startingDealer
 * @param blinds
 */
class Table(players: MutableList<Player>, startingDealer: Player, blinds: Blinds) {

    private val pokerEngine = PokerEngine()

    val gameInfo: GameInfo?
        get() = toGameInfo(pokerEngine.state)

    private fun toGameInfo(state: PokerEngine.State?): GameInfo? {
        if(state == null)
            return null
        return GameInfo(
            state.activePlayer,
            state.dealer,
            state.smallBlind,
            state.bigBlind,
            state.round.name,
            state.blinds,
            state.pot.amount
        )
    }


    init {
        val initCommand = InitCommand(players, startingDealer, blinds)
        pokerEngine.execute(initCommand)
    }

    /**
     * Calls the last bet
     *
     */
    fun call() {
        with(pokerEngine.state!!.round) {
            val amount = highestBet - activePlayer.bets
            pokerEngine.execute(BetCommand(activePlayer.player.bet(amount)))
        }
    }

    /**
     * Creates a new bet that must be higher than the current bet
     *
     * @param chips
     */
    fun raise(chips: ChipsBalance.Chips) {
        pokerEngine.execute(BetCommand(chips))
    }

    /**
     * Check the current bet
     *
     */
    fun check() {
        pokerEngine.execute(CheckCommand())
    }

    /**
     * Remove yourself from the game, forfeiting all made bets
     *
     */
    fun fold() {
        pokerEngine.execute(FoldCommand())
    }

    /**
     * Selects winner of the game
     */
    fun selectWinner(player: Player) {
        pokerEngine.execute(SelectWinnerCommand(player))
    }

}