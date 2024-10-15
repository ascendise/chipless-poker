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
import ch.ascendise.chipless.views.StartScreenViewModel
import ch.ascendise.chipless.views.games.CreateGameView
import ch.ascendise.chipless.views.games.CreateGameViewModel
import ch.ascendise.chipless.views.games.LoadGamesView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val navigator = NavigatorImpl(navController)
    PokerTheme(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Routes.Start.name) {
            composable(Routes.Start.name) {
                StartScreenView(StartScreenViewModel(navigator))
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