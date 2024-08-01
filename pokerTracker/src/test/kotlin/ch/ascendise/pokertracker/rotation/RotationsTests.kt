package ch.ascendise.pokertracker.rotation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RotationsTests {

    @ParameterizedTest
    @MethodSource("rotatingAddTestCases")
    fun `should add two values and handle overflows by rotating back to start`(
        left: Int, right: Int, max: Int, start: Int, expected: Int) {
        //Arrange
        //Act
        val result = Rotations.rotatingAdd(left, right, max, start)
        //Assert
        assertEquals(expected, result)
    }

    private fun rotatingAddTestCases()
        = Stream.of(
            //left right max start expected
            Arguments.of(2, 2, 5, 0, 4),    //normal addition
            Arguments.of(3, 1, 3, 0, 0),    //increment to overflow
            Arguments.of(3, 1, 3, 1, 1),    //increment to overflow - with custom start
            Arguments.of(2, 5, 2, 0, 1),    //multiple overflows
            Arguments.of(0, 0, 0, 0, 0)     //0
    )

    @Test
    fun `should throw when passing a start value greater than max to rotatingAdd`() {
        //Act
        val illegalAdd = { Rotations.rotatingAdd(0, 0, 3, 5) }
        //Assert
        assertThrows<IllegalArgumentException> { illegalAdd() }
    }


    @ParameterizedTest
    @MethodSource("rotatingSubTestCases")
    fun `should subtract right from left and handle overflows by rotating back to start`(
        left: Int, right: Int, max: Int, start: Int, expected: Int) {
        //Arrange
        //Act
        val result = Rotations.rotatingSub(left, right, max, start)
        //Assert
        assertEquals(expected, result)
    }

    private fun rotatingSubTestCases()
            = Stream.of(
        //left right max start expected
        Arguments.of(3, 1, 3, 0, 2),    //normal subtraction
        Arguments.of(0, 1, 4, 0, 4),    //decrement to underflow
        Arguments.of(3, 7, 3, 0, 0),    //multiple overflows
        Arguments.of(0, 0, 0, 0, 0)     //0
    )

    @Test
    fun `should throw when passing a start value greater than max to rotatingSubtract`() {
        //Act
        val illegalSub = { Rotations.rotatingSub(0, 0, 3, 5) }
        //Assert
        assertThrows<IllegalArgumentException> { illegalSub() }
    }
}