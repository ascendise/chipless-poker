package ch.ascendise.pokertracker

import ch.ascendise.pokertracker.engine.PokerEngine
import ch.ascendise.pokertracker.engine.RulesEngine
import ch.ascendise.pokertracker.engine.commands.InitCommand

/**
 * Tracks poker game state and is the center of the poker lib
 *
 * @property players list of players in the game
 * @property dealer starting dealer to define blinds, active player, etc.
 * @property blinds starting blinds
 */
class Table(private val players: MutableList<Player>,
            private val dealer: Player,
            private val blinds: PokerEngine.Blinds = PokerEngine.Blinds(1, 2),
            private val rulesEngine: RulesEngine<PokerEngine.State, PokerEngine.Command>) {

    val state: PokerEngine.State?
        get() = rulesEngine.state


    init {
        val initCommand = InitCommand(players, dealer, blinds)
        rulesEngine.execute(initCommand)
    }

    fun play(command: PokerEngine.Command) {
        rulesEngine.execute(command)
    }

}