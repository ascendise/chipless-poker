package ch.ascendise.chipless.navigation

import androidx.navigation.NavHostController
import ch.ascendise.chipless.Routes

internal class NavigatorImpl(private val navHostController: NavHostController) : Navigator {

    override fun navigateTo(route: String) {
        navHostController.navigate(route)
    }

}