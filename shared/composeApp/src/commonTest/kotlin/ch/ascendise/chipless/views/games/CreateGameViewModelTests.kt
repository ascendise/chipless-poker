package ch.ascendise.chipless.views.games

import kotlin.test.*

class CreateGameViewModelTests {

    @Test
    fun shouldAddNewPlayer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        //Act
        viewModel.createNewPlayer()
        //Assert
        val players = viewModel.state.players
        assertEquals(1, players.count())
        val player = viewModel.state.players.firstOrNull()
        assertNotNull(player)
        assertEquals("Player #1", player.name)
        assertEquals(1, viewModel.state.playersLeft.value)
    }

    @Test
    fun shouldDefineFirstPlayerAsDealer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        //Act
        viewModel.createNewPlayer()
        //Assert
        assertNotNull(viewModel.state.dealer)
        assertSame(viewModel.state.players.first(), viewModel.state.dealer.value)
    }

    @Test
    fun shouldChangeDealer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        viewModel.createNewPlayer()
        viewModel.createNewPlayer()
        //Act
        viewModel.changeDealer(1)
        //Assert
        assertSame(viewModel.state.players[1], viewModel.state.dealer.value)
    }

    @Test
    fun shouldThrowWhenTryingToSetInvalidPlayerIndexAsDealer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        viewModel.createNewPlayer()
        viewModel.createNewPlayer()
        //Act
        //Assert
        val exception = assertFails {
            viewModel.changeDealer(192034295)
        }
        assertIs<IndexOutOfBoundsException>(exception)
    }

    @Test
    fun shouldAddMultiplePlayer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        //Act
        viewModel.createNewPlayer()
        viewModel.createNewPlayer()
        //Assert
        val players = viewModel.state.players
        assertEquals(2, players.count())
        val player = viewModel.state.players.lastOrNull()
        assertNotNull(player)
        assertEquals("Player #2", player.name)
        assertEquals(0, viewModel.state.playersLeft.value)
    }

    @Test
    fun shouldRemovePlayer() {
        //Arrange
        val viewModel = CreateGameViewModel()
        viewModel.createNewPlayer()
        viewModel.createNewPlayer()
        viewModel.createNewPlayer()
        //Act
        val players = viewModel.state.players
        viewModel.deletePlayer(players[1])
        //Assert
        assertEquals(2, players.count())
        assertTrue("Wrong player deleted (Player #1)") { players.any { it.name == "Player #1" } }
        assertTrue("Wrong player deleted (Player #3)") { players.any { it.name == "Player #3" } }
        assertEquals(0, viewModel.state.playersLeft.value)
    }
}