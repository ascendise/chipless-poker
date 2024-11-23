package ch.ascendise.chipless.positioning

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.test.*

class RectangleTests {

    @Test
    fun `should split rectangle evenly and return all points on the rectangle`() {
        //Arrange
        val rectangle = Rectangle(20.0, 20.0)
        //Act
        var points = rectangle.splitEvenly(8)
        //Assert
        //Round values in points so tests dont fail because of floating point inaccuracy
        points = points
            .map { Point(it.x.round(4), it.y.round(4)) }
            .toTypedArray()
        val expectedPoints = arrayOf(
            Point(0.0, 10.0),
            Point(10.0, 10.0),
            Point(10.0, 0.0),
            Point(10.0, -10.0),
            Point(0.0, -10.0),
            Point(-10.0, -10.0),
            Point(-10.0, 0.0),
            Point(-10.0, 10.0),
        )
        assertContentEquals(expectedPoints, points)
    }

    private fun Double.round(decimalPlaces: Int): Double {
        val multiplier = 10.pow(decimalPlaces)
        var value = this * multiplier
        value = if(value > 0){
            floor(value)
        } else {
            ceil(value)
        }
        if(value == -0.0)
            value = 0.0
        return value / multiplier
    }

    private fun Int.pow(of: Int): Int {
        var value = this
        for(i in 1..of)
            value *= this
        return value
    }

    @Test
    fun `should not allow rectangle with zero width or height`() {
        assertFailsWith<IllegalArgumentException>{Rectangle(0.0, 0.0)}
    }

}