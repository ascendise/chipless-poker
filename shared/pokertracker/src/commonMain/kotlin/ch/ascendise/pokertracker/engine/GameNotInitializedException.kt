package ch.ascendise.pokertracker.engine

import ch.ascendise.pokertracker.InvalidPlayException

/**
 * Thrown when a command is executed on an uninitialized game
 *
 */
class GameNotInitializedException : InvalidPlayException("Initialize game first!")