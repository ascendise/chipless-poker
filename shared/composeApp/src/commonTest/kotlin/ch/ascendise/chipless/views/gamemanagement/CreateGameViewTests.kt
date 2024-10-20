package ch.ascendise.chipless.views.gamemanagement

import androidx.compose.ui.test.*
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.add_1_players
import chiplesspoker.shared.composeapp.generated.resources.add_2_players
import chiplesspoker.shared.composeapp.generated.resources.add_player_button
import chiplesspoker.shared.composeapp.generated.resources.start_game
import org.jetbrains.compose.resources.stringResource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class CreateGameViewTests {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldShowPlayerDefinitionWidgetAtStart() = runComposeUiTest {
        //Arrange
        var add2Players = ""
        setContent {
            CreateGameView()
            add2Players = stringResource(Res.string.add_2_players)
        }
        //Act
        //Assert
        onNodeWithText(add2Players).assertIsNotEnabled()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldAddNewPlayerAndUpdateButtonText() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        var add1Player = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
            add1Player = stringResource(Res.string.add_1_players)
        }
        //Act
        onNodeWithText(addPlayerButton).performClick()
        //Assert
        onNodeWithText("Player #1").assertExists("Player was not added")
        onNodeWithText(add1Player).assertExists("Button text was not updated")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldEnableButtonWhenEnoughPlayersWereAdded() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        var createNewGameButton = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
            createNewGameButton = stringResource(Res.string.start_game)
        }
        //Act
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Assert
        onNodeWithText(createNewGameButton).assertIsEnabled()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldRemovePlayerWhenSlidingCardAway() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
        }
        //Create some players
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Act
        //Hard to simulate the drag event through UI so deletion is triggered directly
        //through the view model
        val playerToDelete = viewModel.state.players[1]
        viewModel.deletePlayer(playerToDelete)
        //Assert
        onNodeWithText("Player #1").assertExists("Wrong player deleted!")
        onNodeWithText("Player #3").assertExists("Wrong player deleted!")
        onNodeWithText("Player #2").assertDoesNotExist()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldDisableButtonIfTooManyPlayersGotRemoved() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        var add1PlayerCaption = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
            add1PlayerCaption = stringResource(Res.string.add_1_players)
        }
        //Create some players
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Act
        val playerToDelete = viewModel.state.players[1]
        viewModel.deletePlayer(playerToDelete)
        //Assert
        onNodeWithText(add1PlayerCaption).assertExists()
        onNodeWithText(add1PlayerCaption).assertIsNotEnabled()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldDefinePlayerAsDealer() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
        }
        //Act
            //Create some players
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Assert
        onNodeWithText("(Dealer)").assertExists()
        onNodeWithTag("setAsDealer(Player #1)").assertIsSelected()
        assertEquals(0, viewModel.state.dealer.value)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldChangeDealer() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                onChangeDealer = viewModel::changeDealer,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
        }
        //Create some players
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Act
        onNodeWithTag("setAsDealer(Player #2)").performClick()
        //Assert
        onNodeWithTag("setAsDealer(Player #2)").assertIsSelected()
        assertEquals(1, viewModel.state.dealer.value)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldChangePlayerBalance() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                onChangeDealer = viewModel::changeDealer,
                state = viewModel.state
            )
        }
        //Act
        onNodeWithText("100").performTextReplacement("200")
        //Assert
        assertEquals(200, viewModel.state.balancePerPlayer.value)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldNotCrashTheAppWhenClearingTheBalanceFieldBecauseItCantCastEmptyStringToInt() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                onChangeDealer = viewModel::changeDealer,
                state = viewModel.state
            )
        }
        //Act
        onNodeWithText("100").performTextClearance()
        //Assert
        assertEquals(0, viewModel.state.balancePerPlayer.value)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldCreateGame() = runComposeUiTest {
        //Arrange
        val viewModel = CreateGameViewModel()
        var addPlayerButton = ""
        var startGame = ""
        setContent {
            CreateGameView(
                onCreateNewPlayer = viewModel::createNewPlayer,
                onChangeDealer = viewModel::changeDealer,
                onStartGame = viewModel::createGame,
                state = viewModel.state
            )
            addPlayerButton = stringResource(Res.string.add_player_button)
            startGame = stringResource(Res.string.start_game)
        }
        onNodeWithText(addPlayerButton).performClick()
        onNodeWithText(addPlayerButton).performClick()
        //Act
        onNodeWithText(startGame).performClick()
        //Assert
        //TODO: Check that a correct game is created when the UI for the game is being made
        //For now just tests that creating a game does not kill the app :)
    }

}