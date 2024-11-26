package ch.ascendise.chipless.positioning

import kotlin.math.atan2

/**
 * Point in a cartesian coordinate system
 *
 * @property x
 * @property y
 */
data class Point(val x: Double, val y: Double) {

    /**
     * Returns the angle of the vector from origin (0,0) to this point in radian
     * The angle ranges between -180 and 180 with angle zero being at x = 1, y = 0
     */
    fun getAngle()
        = atan2(y, x)
}