package ch.ascendise.pokertracker

enum class BettingRounds {
    /** Cards dealt to players **/
    Hole,
    /** Three cards on table**/
    Flop,
    /** Four cards on table**/
    Turn,
    /** Five cards on table**/
    River,
    /** Winner selection **/
    Reward,
}