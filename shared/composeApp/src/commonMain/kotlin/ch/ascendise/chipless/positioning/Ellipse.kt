package ch.ascendise.chipless.positioning

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal class Ellipse(
    val radiusX: Double,
    val radiusY: Double
) {

    fun splitEvenly(points: Int): Array<Point> {
        val angle = 360.0 / points
        val vertices: MutableList<Point> = mutableListOf()
        for(i in (0..<points)) {
            val currentAngle = (i * angle).toRadian()
            val point = Point(
                radiusX * cos(currentAngle),
                radiusY * sin(currentAngle)
            )
            vertices.add(point)
        }
        return vertices.toTypedArray()
    }

    private fun Double.toRadian()
        = this / (180 / PI)
}