package ch.ascendise.pokertracker

import kotlin.math.min

/**
 * Tracks poker game state and is the center of the poker lib
 *
 * @property players list of players in the game
 * @property dealer starting dealer to define paid blinds, active player, etc.
 * @property blinds starting blinds
 */
class Table(private val players: ArrayList<Player>,
            private val dealer: Player,
            private val blinds: Blinds = Blinds(1, 2)) {

    private var currentRound: BettingRounds = BettingRounds.Hole
    private val pot = ChipsBalanceImpl(0, null)
    private val smallBlind: Player
        get() = players[(players.indexOf(dealer) + 1) % players.count()]
    private val bigBlind: Player
        get() = players[(players.indexOf(dealer) + 2) % players.count()]

    /**
     * Current pot on the table
     */
    val currentPot: Int
        get() = pot.amount

    /**
     * Current player allowed to make a bet
     */
    var activePlayer: Player
        private set

    init {
        activePlayer = initActivePlayer()
        payBlinds()
    }

    private fun initActivePlayer(): Player {
        val dealerIndex = players.indexOf(dealer)
        val diffToFirstBet = 3
        val activePlayerIndex = rotatingAdd(dealerIndex, diffToFirstBet, players.count())
        return players[activePlayerIndex]
    }


    /**
     * Adds two values. If the sum is more than max, the value resets at the start value
     *
     * Example: left = 2, right = 5, max = 4, start = -1
     * Result = 2
     *
     * @param left
     * @param right
     * @param max
     * @param start
     */
    private fun rotatingAdd(left: Int, right: Int, max: Int, start: Int = 0)
        = (left + right) % max + start

    private fun payBlinds() {
        forceBet(smallBlind, blinds.small)
        forceBet(bigBlind, blinds.big)
    }

    private fun forceBet(player: Player, amount: Int) {
        player.bet(min(amount, player.balance.amount)).use { bet ->
            pot.deposit(bet)
        }
    }

    fun bet(chips: ChipsBalance.Chips) {
        if(chips.owner != activePlayer)
            throw WrongPlayerException()
        pot.deposit(chips)
        rotateActivePlayer(1)
    }

    private fun rotateActivePlayer(steps: Int = 1) {
        val activeIndex = players.indexOf(activePlayer)
        activePlayer = players[rotatingAdd(activeIndex, steps, players.count())]
    }

    enum class BettingRounds {
        Hole, //Two cards dealt
        Flop, //Three community cards
        Turn, //Four community cards
        River //Five community cards
    }

    data class Blinds(val small: Int, val big: Int) {
        init {
           if(small > big)
               throw IllegalArgumentException("Big Blinds cannot be equal or less than small blinds")
        }
    }

}