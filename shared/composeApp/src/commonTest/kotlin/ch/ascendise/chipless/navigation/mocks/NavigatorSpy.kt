package ch.ascendise.chipless.views.navigation.mocks

import ch.ascendise.chipless.navigation.Navigator

internal class NavigatorSpy : Navigator {

    private val _navigations: MutableList<String> = mutableListOf()
    val navigations: List<String>
        get() = _navigations.toList()

    override fun navigateTo(route: String) {
        _navigations.add(route)
    }
}