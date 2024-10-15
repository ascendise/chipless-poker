package ch.ascendise.chipless.navigation

import ch.ascendise.chipless.Routes

internal interface Navigator {
    fun navigateTo(route: String)
}