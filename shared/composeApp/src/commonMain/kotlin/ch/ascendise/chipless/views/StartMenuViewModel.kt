package ch.ascendise.chipless.views

import androidx.lifecycle.ViewModel
import ch.ascendise.chipless.Routes
import ch.ascendise.chipless.navigation.Navigator

internal class StartMenuViewModel(private val navigator: Navigator): ViewModel() {

    /**
     * Opens the view to create a new game
     */
    fun createNewGame()
        = navigator.navigateTo(Routes.NewGame.name)

    /**
     * Opens the view to list existing games
     */
    fun loadExistingGames()
        = navigator.navigateTo(Routes.LoadGames.name)

    /**
     * Opens the view to edit settings
     */
    fun editSettings()
        = navigator.navigateTo(Routes.Settings.name)

}