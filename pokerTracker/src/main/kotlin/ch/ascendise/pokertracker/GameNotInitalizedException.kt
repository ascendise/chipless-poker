package ch.ascendise.pokertracker

/**
 * Thrown when a command is executed on an uninitialized game
 *
 */
class GameNotInitalizedException : InvalidPlayException("Initialize game first!")