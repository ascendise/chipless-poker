package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.BettingRounds
import ch.ascendise.pokertracker.Blinds
import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.engine.commands.BetCommand
import ch.ascendise.pokertracker.engine.commands.InitCommand
import kotlin.test.*

class PokerEngineTests {

    @Test
    fun `should move to next round as soon as everyone has bet`() {
        //Arrange
        val pokerEngine = PokerEngine()
        val players = mutableListOf(
            Player(100),
            Player(100),
            Player(100)
        )
        //Act
        pokerEngine.execute(InitCommand(players, players[0], Blinds(1, 2)))
            //Bet through one round
        with(pokerEngine.state!!) {
            pokerEngine.execute(BetCommand(activePlayer.bet(4)))
            pokerEngine.execute(BetCommand(activePlayer.bet(3)))
            pokerEngine.execute(BetCommand(activePlayer.bet(2)))
        }
        //Assert
        assertEquals(BettingRounds.Flop, pokerEngine.state!!.round.name)
    }

    @Test
    fun `should move to next round as soon as everyone has bet the same amount`() {
        //Arrange
        val pokerEngine = PokerEngine()
        val players = mutableListOf(
            Player(100), //dealer, starts game
            Player(100),
            Player(100)
        )
        //Act
        pokerEngine.execute(InitCommand(players, players[0], Blinds(1, 2)))
        //Bet through one round
        with(pokerEngine.state!!) {
            pokerEngine.execute(BetCommand(activePlayer.bet(4))) //Raise to 4
            pokerEngine.execute(BetCommand(activePlayer.bet(3))) //Call
            pokerEngine.execute(BetCommand(activePlayer.bet(4))) //Raise to 6
            pokerEngine.execute(BetCommand(activePlayer.bet(2))) //Call
            pokerEngine.execute(BetCommand(activePlayer.bet(2))) //Call

            //Assert
            assertEquals(BettingRounds.Flop, pokerEngine.state!!.round.name)
            assertSame(smallBlind, activePlayer)
        }
    }
}