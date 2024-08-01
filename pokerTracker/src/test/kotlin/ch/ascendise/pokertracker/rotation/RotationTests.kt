package ch.ascendise.pokertracker.rotation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RotationTests {

    @Test
    fun `should start from beginning when reaching end during iteration`() {
        //Arrange
        val intRotation = Rotation((0..2).toList())
        //Act
        val items = mutableListOf<Int>()
        var counter = 0
        for(item in intRotation) {
            items.add(item)
            if(counter == 4)
                break
            counter++
        }
        //Assert
        assertEquals(listOf(0, 1, 2, 0 ,1), items, "Collection did not rotate")
    }

    @Test
    fun `should throw if rotation is empty`() {
        //Arrange
        //Act
        val illegalRotation = { Rotation<Int>(emptyList())}
        //Assert
        assertThrows<IllegalArgumentException> { illegalRotation() }
    }

    @Test
    fun `should throw if selected starting index is out of bounds`() {
        //Arrange
        //Act
        val illegalRotation = { Rotation((0..2).toList(), 420)}
        //Assert
        assertThrows<IndexOutOfBoundsException> { illegalRotation() }
    }

    @Test
    fun `should skip to next player by specified steps`() {
        //Arrange
        val rotation = Rotation((0..4).toList(), 0)
        //Act
        val result = rotation.next(3)
        //Assert
        assertEquals(3, result)
    }

    @ParameterizedTest
    @ValueSource(ints = [0,1,2,3,4])
    fun `should return specified index on next`(nextIndex: Int) {
        //Arrange
        val rotation = Rotation((0..4).toList(), 0)
        //Act
        rotation.setNext(nextIndex)
        val result = rotation.next()
        //Assert
        assertEquals(nextIndex, result)
    }

    @Test
    fun `should return next item without rotating`() {
        //Arrange
        val rotation = Rotation((0..4).toList(), 0)
        //Act
        //Assert
        assertEquals(1, rotation.peekNext())
        assertEquals(1, rotation.peekNext())
        assertEquals(1, rotation.peekNext())
    }

    @Test
    fun `should return next item after x steps without rotating`() {

        //Arrange
        val rotation = Rotation((0..4).toList(), 0)
        //Act
        //Assert
        assertEquals(2, rotation.peekNext(2))
        assertEquals(2, rotation.peekNext(2))
        assertEquals(2, rotation.peekNext(2))
    }
}