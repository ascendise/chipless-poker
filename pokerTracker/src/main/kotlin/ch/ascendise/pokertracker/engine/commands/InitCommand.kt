package ch.ascendise.pokertracker.engine.commands

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.chips.ChipsBalance
import ch.ascendise.pokertracker.chips.ChipsBalanceImpl
import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.rotation.Rotation
import ch.ascendise.pokertracker.rotation.Rotations

/**
 * Command to initialize a new poker game
 *
 * @property players
 * @property dealer starting dealer for the game
 * @property blinds
 */
data class InitCommand(
    val players: List<Player>,
    val dealer: Player,
    val blinds: PokerEngine.Blinds = PokerEngine.Blinds(1, 2)
    ) : PokerEngine.Command {

    init {
        if(players.count() < 2)
            throw IllegalArgumentException("At least 2 players required")
        if(!players.contains(dealer))
            throw IllegalArgumentException("Dealer has to be part of player list!")
    }

    override fun execute(state: PokerEngine.State?): PokerEngine.State {
        val seats = players.stream().map { PokerEngine.Seat(it) }.toList()
        val rotation = Rotation(seats, 0)
        val newState = PokerEngine.State(
            players = players,
            dealer = dealer,
            smallBlind = rotation.peekNext().player,
            bigBlind = rotation.peekNext(2).player,
            blinds = blinds,
            pot = ChipsBalanceImpl(0, null),
            round = PokerEngine.Round(rotation, getActivePlayer(), PokerEngine.BettingRounds.Hole)
        )
        payBlinds(newState)
        return newState
    }

    private fun payBlinds(state: PokerEngine.State) {
        forceBet(state.smallBlind, state.blinds.small, state.pot)
        state.round.getSeat(state.smallBlind).bets += state.blinds.small
        forceBet(state.bigBlind, state.blinds.big, state.pot)
        state.round.getSeat(state.bigBlind).bets += state.blinds.big
    }

    private fun forceBet(source: Player, amount: Int, target: ChipsBalance) {
        source.bet(amount).use { chips ->
            target.deposit(chips)
        }
    }

    private fun getActivePlayer(): Player {
        if(players.count() <= 3)
            return dealer
        val dealerIndex = players.indexOf(dealer)
        val index = Rotations.rotatingAdd(dealerIndex, 3, players.count() - 1)
        return players[index]
    }
}