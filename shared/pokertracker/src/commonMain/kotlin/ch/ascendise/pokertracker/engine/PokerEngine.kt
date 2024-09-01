package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.commands.PlayerCommand
import ch.ascendise.pokertracker.engine.rules.EndOfRoundRule
import ch.ascendise.pokertracker.engine.rules.RewardWinnerRule
import ch.ascendise.pokertracker.rotation.Rotation
import ch.ascendise.pokertracker.rotation.Rotations

internal class PokerEngine : RulesEngine<PokerEngine.State, PokerEngine.Command>(afterRules = getAfterRules()) {

    companion object {
        private fun getAfterRules(): Iterable<Rule<State>>
            = listOf(
                RewardWinnerRule(),
                EndOfRoundRule()
            )
    }

    override fun onExecute(command: Command) {
        state = command.execute(state)
        if(command is PlayerCommand) {
            state!!.round.actionCount++
            state!!.rotateActivePlayer()
        }
    }


    private fun State.rotateActivePlayer() {
        round.activePlayer = round.players.next()
    }

    /**
     * State of the current game. Game being one sequence of betting rounds from Hole to River
     *
     * @property players
     * @property smallBlind
     * @property bigBlind
     * @property dealer
     * @property blinds
     * @property pot
     * @property round
     */
    data class State(
        val players: List<Player>,
        var smallBlind: Player,
        var bigBlind: Player,
        var dealer: Player,
        var blinds: Blinds = Blinds(1, 2),
        val pot: ChipsBalance,
        var round: Round
    ){

        val activePlayer: Player
            get() = round.activePlayer.player
    }

    interface Command {
        fun execute(state: State?): State?
    }

    /**
     * Betting round. Part of a poker game
     *
     * @property players
     * @property startingPlayer
     * @property name
     * @property activePlayer
     */
    data class Round(val players: Rotation<Seat>,
                     val startingPlayer: Player,
                     val name: BettingRounds,
                     var activePlayer: Seat = getSeat(players, startingPlayer),
                     var actionCount: Int = 0) {

        val highestBet: Int
            get() = players.items.maxOfOrNull { it.bets } ?: 0

        init {
            players.setNext(
                Rotations.rotatingAdd(
                    players.items.indexOf(activePlayer),
                    1,
                    players.items.count() - 1
                )
            )
        }

        companion object {
            fun getSeat(seats: Rotation<Seat>, player: Player): Seat
                = seats.items
                    .firstOrNull { it.player == player }
                    ?: throw IllegalArgumentException(
                           "Player is not in provided rotation!")

        }

        fun getSeat(player: Player): Seat
            = getSeat(players, player)
    }

    data class Seat(val player: Player, var bets: Int = 0)

}