package ch.ascendise.chipless

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.ascendise.chipless.navigation.NavigatorImpl
import ch.ascendise.chipless.views.SettingsView
import ch.ascendise.chipless.views.StartScreenView
import ch.ascendise.chipless.views.StartMenuViewModel
import ch.ascendise.chipless.views.gamemanagement.CreateGameView
import ch.ascendise.chipless.views.gamemanagement.CreateGameViewModel
import ch.ascendise.chipless.views.gamemanagement.LoadGamesView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val navigator = NavigatorImpl(navController)
    PokerTheme(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Routes.Start.name) {
            composable(Routes.Start.name) {
                StartScreenView(StartMenuViewModel(navigator))
            }
            composable(Routes.NewGame.name) {
                val viewModel = CreateGameViewModel()
                CreateGameView(
                    onCreateNewPlayer = viewModel::createNewPlayer,
                    onDeletePlayer = viewModel::deletePlayer,
                    onChangeDealer = viewModel::changeDealer,
                    state = viewModel.state
                )
            }
            composable(Routes.LoadGames.name) { LoadGamesView() }
            composable(Routes.Settings.name) { SettingsView() }
        }
    }
}