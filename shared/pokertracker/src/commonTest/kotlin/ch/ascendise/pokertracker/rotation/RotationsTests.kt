package ch.ascendise.pokertracker.rotation

import kotlin.test.*

class RotationsTests {

    private fun rotatingAddTest(
        left: Int, right: Int, max: Int, start: Int, expected: Int) {
        //Arrange
        //Act
        val result = Rotations.rotatingAdd(left, right, max, start)
        //Assert
        assertEquals(expected, result)
    }

    @Test
    fun `rotatingAdd should handle normal addition`() {
        rotatingAddTest(2, 2, 5, 0 ,4)
    }

    @Test
    fun `rotatingAdd should overflow on increment`() {
        rotatingAddTest(3, 1, 3, 0, 0)
    }

    @Test
    fun `rotatingAdd should overflow on increment and handle custom start`() {
        rotatingAddTest(3, 1, 3, 1, 1)
    }

    @Test
    fun `rotatingAdd should handle multiple overflows`() {
        rotatingAddTest(2, 5, 2, 0, 1)
    }

    @Test
    fun `rotatingAdd should handle all zeroes!`() {
        rotatingAddTest(0, 0, 0, 0, 0)
    }

    @Test
    fun `should throw when passing a start value greater than max to rotatingAdd`() {
        //Act
        val illegalAdd = { Rotations.rotatingAdd(0, 0, 3, 5) }
        //Assert
        assertFailsWith<IllegalArgumentException> { illegalAdd() }
    }

    private fun rotatingSubTest(
        left: Int, right: Int, max: Int, start: Int, expected: Int) {
        //Arrange
        //Act
        val result = Rotations.rotatingSub(left, right, max, start)
        //Assert
        assertEquals(expected, result)
    }

    @Test
    fun `rotatingSub should handle normal subtraction`() {
        rotatingSubTest(3, 1, 3, 0, 2)
    }

    @Test
    fun `rotatingSub should handle decrement to underflow`() {
        rotatingSubTest(0, 1, 4, 0, 4)
    }

    @Test
    fun `rotatingSub should handle multiple underflows`() {
        rotatingSubTest(3, 7, 3, 0, 0)
    }

    @Test
    fun `rotatingSub should handle all zeroes!`() {
        rotatingSubTest(0, 0, 0, 0, 0)
    }

    @Test
    fun `should throw when passing a start value greater than max to rotatingSubtract`() {
        //Act
        val illegalSub = { Rotations.rotatingSub(0, 0, 3, 5) }
        //Assert
        assertFailsWith<IllegalArgumentException> { illegalSub() }
    }
}