package ch.ascendise.pokertracker

/**
 * Thrown when an illegal game action was tried, like betting more than available
 */
abstract class InvalidPlayException(message: String) : Exception(message)