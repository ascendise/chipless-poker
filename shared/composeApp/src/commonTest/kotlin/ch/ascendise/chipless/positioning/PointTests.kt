package ch.ascendise.chipless.positioning

import kotlin.math.PI
import kotlin.test.*

class PointTests {

    @Test
    fun `should return correct angle`() {
        shouldReturnCorrectAngle(Point(1.0, 0.0), radian(0.0))
        shouldReturnCorrectAngle(Point(1.0, 1.0), radian(45.0))
        shouldReturnCorrectAngle(Point(1.0, -1.0), radian(-45.0))
        shouldReturnCorrectAngle(Point(0.0, -1.0), radian(-90.0))
        shouldReturnCorrectAngle(Point(-1.0, -1.0), radian(-135.0))
    }

    private fun radian(degree: Double)
        = degree / 180 * PI

    private fun shouldReturnCorrectAngle(point: Point, expectedAngle: Double) {
        //Arrange
        //Act
        val angle = point.getAngle()
        //Assert
        assertEquals(expectedAngle, angle)
    }
}