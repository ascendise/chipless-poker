package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.engine.rules.EndOfRoundRule
import ch.ascendise.pokertracker.rotation.Rotation
import ch.ascendise.pokertracker.rotation.Rotations
import kotlin.jvm.optionals.getOrElse

class PokerEngine : RulesEngine<PokerEngine.State, PokerEngine.Command>(afterRules = getAfterRules()) {

    companion object {
        private fun getAfterRules(): Iterable<Rule<State>>
            = listOf(
                EndOfRoundRule()
            )
    }

    override fun onExecute(command: Command) {
        state = command.execute(state)
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
        fun execute(state: State?): State
    }

    data class Blinds(val small: Int, val big: Int) {
        init {
           if(small > big)
               throw IllegalArgumentException("Big Blinds cannot be less than small blinds")
        }
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
                     var activePlayer: Seat = getSeat(players, startingPlayer)) {

        val highestBet: Int
            get() = players.items.stream().mapToInt { it.bets }.max().asInt

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
                = seats.items.stream()
                    .filter { it.player == player }
                    .findFirst()
                    .getOrElse { throw IllegalArgumentException(
                        "Player is not in provided rotation!")
                    }
        }

        fun getSeat(player: Player): Seat
            = getSeat(players, player)
    }

    data class Seat(val player: Player, var bets: Int = 0)

    enum class BettingRounds {
        Hole, //Two cards dealt
        Flop, //Three community cards
        Turn, //Four community cards
        River //Five community cards
    }
}