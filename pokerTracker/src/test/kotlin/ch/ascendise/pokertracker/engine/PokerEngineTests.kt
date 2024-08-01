package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.Player
import ch.ascendise.pokertracker.engine.commands.BetCommand
import ch.ascendise.pokertracker.engine.commands.InitCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class PokerEngineTests {

    @Test
    fun `should move to next round as soon as everyone has bet`() {
        //Arrange
        val pokerEngine = PokerEngine()
        val players = listOf(
            Player(100),
            Player(100),
            Player(100)
        )
        //Act
        pokerEngine.execute(InitCommand(players, players[0], PokerEngine.Blinds(1, 2)))
            //Bet through one round
        with(pokerEngine.state!!) {
            pokerEngine.execute(BetCommand(activePlayer.bet(4)))
            pokerEngine.execute(BetCommand(activePlayer.bet(3)))
            pokerEngine.execute(BetCommand(activePlayer.bet(2)))
        }
        //Assert
        assertEquals(PokerEngine.BettingRounds.Flop, pokerEngine.state!!.round.name)
    }

    @Test
    fun `should move to next round as soon as everyone has bet the same amount`() {
        //Arrange
        val pokerEngine = PokerEngine()
        val players = listOf(
            Player(100), //dealer, starts game
            Player(100),
            Player(100)
        )
        //Act
        pokerEngine.execute(InitCommand(players, players[0], PokerEngine.Blinds(1, 2)))
        //Bet through one round
        with(pokerEngine.state!!) {
            pokerEngine.execute(BetCommand(activePlayer.bet(4))) //Raise to 4
            pokerEngine.execute(BetCommand(activePlayer.bet(3))) //Call
            pokerEngine.execute(BetCommand(activePlayer.bet(4))) //Raise to 6
            pokerEngine.execute(BetCommand(activePlayer.bet(2))) //Call
            pokerEngine.execute(BetCommand(activePlayer.bet(2))) //Call

            //Assert
            assertEquals(PokerEngine.BettingRounds.Flop, pokerEngine.state!!.round.name)
            assertSame(smallBlind, activePlayer)
        }
    }
}