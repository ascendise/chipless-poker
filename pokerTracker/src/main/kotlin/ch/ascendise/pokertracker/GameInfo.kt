package ch.ascendise.pokertracker

data class GameInfo(
    val activePlayer: Player,
    val dealer: Player,
    val smallBlind: Player,
    val bigBlind: Player,
    val round: BettingRounds,
    val blinds: Blinds,
    val pot: Int
)
