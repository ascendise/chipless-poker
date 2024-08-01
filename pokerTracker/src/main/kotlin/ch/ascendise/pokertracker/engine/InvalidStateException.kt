package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.InvalidPlayException

/**
 * Thrown when a player action is not allowed at the current game state
 *
 * @param message
 */
class InvalidStateException(message: String) : InvalidPlayException(message)