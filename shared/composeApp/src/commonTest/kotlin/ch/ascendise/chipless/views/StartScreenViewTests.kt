package ch.ascendise.chipless.views

import androidx.compose.ui.test.*
import ch.ascendise.chipless.Routes
import ch.ascendise.chipless.views.navigation.mocks.NavigatorSpy
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.create_new_game_button
import chiplesspoker.shared.composeapp.generated.resources.load_existing_game_button
import chiplesspoker.shared.composeapp.generated.resources.settings_button
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.test.Test
import kotlin.test.assertEquals

class StartScreenViewTests {

    @OptIn(ExperimentalTestApi::class)
    private fun shouldNavigateToView(expectedView: Routes, buttonTextResource: StringResource) = runComposeUiTest {
        //Arrange
        val navigatorSpy = NavigatorSpy()
        var buttonText = ""
        setContent {
            StartScreenView(StartMenuViewModel(navigatorSpy))
            buttonText = stringResource(buttonTextResource)
        }
        //Act
        onNodeWithText(buttonText).performClick()
        //Assert
        assertEquals(1, navigatorSpy.navigations.count())
        assertEquals(expectedView.name, navigatorSpy.navigations.first())
    }

    @Test
    fun shouldNavigateToNewGameView() {
        shouldNavigateToView(Routes.NewGame, Res.string.create_new_game_button)
    }

    @Test
    fun shouldNavigateToLoadGamesView() {
        shouldNavigateToView(Routes.LoadGames, Res.string.load_existing_game_button)
    }

    @Test
    fun shouldNavigateToSettings() {
        shouldNavigateToView(Routes.Settings, Res.string.settings_button)
    }
}