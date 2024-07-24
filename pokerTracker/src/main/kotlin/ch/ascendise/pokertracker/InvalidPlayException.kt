package ch.ascendise.pokertracker

import java.lang.Exception

/**
 * Thrown when an illegal game action was tried, like betting more than available
 */
abstract class InvalidPlayException(message: String) : Exception(message)