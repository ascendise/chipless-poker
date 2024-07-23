package ch.ascendise.pokertracker

class Table(private val players: ArrayList<Player>, private val dealer: Player) {

    var activePlayer: Player
        private set
    private var currentRound: BettingRounds = BettingRounds.Hole
    private var pot: Int = 0

    init {
        activePlayer = initActivePlayer()
    }

    private fun initActivePlayer(): Player {
        val dealerIndex = players.indexOf(dealer)
        val diffToFirstBet = 3
        val activePlayerIndex = (dealerIndex + diffToFirstBet) % players.count()
        return players[activePlayerIndex]
    }


    enum class BettingRounds {
        Hole, //Two cards dealt
        Flop, //Three community cards
        Turn, //Four community cards
        River //Five community cards
    }

}