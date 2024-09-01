package ch.ascendise.pokertracker.rotation

import kotlin.test.*

class RotationTests {

    @Test
    fun `should start from beginning when reaching end during iteration`() {
        //Arrange
        val intRotation = Rotation((0..2).toMutableList())
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
        val illegalRotation = { Rotation<Int>(mutableListOf())}
        //Assert
        assertFailsWith<IllegalArgumentException> { illegalRotation() }
    }

    @Test
    fun `should throw if selected starting index is out of bounds`() {
        //Arrange
        //Act
        val illegalRotation = { Rotation((0..2).toMutableList(), 420)}
        //Assert
        assertFailsWith<IndexOutOfBoundsException> { illegalRotation() }
    }

    @Test
    fun `should skip to next player by specified steps`() {
        //Arrange
        val rotation = Rotation((0..4).toMutableList(), 0)
        //Act
        val result = rotation.next(3)
        //Assert
        assertEquals(3, result)
    }

    private fun setNextTest(nextIndex: Int) {
        //Arrange
        val rotation = Rotation((0..4).toMutableList(), 0)
        //Act
        rotation.setNext(nextIndex)
        val result = rotation.next()
        //Assert
        assertEquals(nextIndex, result)
    }

    @Test
    fun `should return specified index 0`() {
        setNextTest(0)
    }

    @Test
    fun `should return specified index 1`() {
        setNextTest(1)
    }

    @Test
    fun `should return specified index 2`() {
        setNextTest(2)
    }

    @Test
    fun `should return specified index 3`() {
        setNextTest(3)
    }

    @Test
    fun `should return specified index 4`() {
        setNextTest(4)
    }

    @Test
    fun `should return next item without rotating`() {
        //Arrange
        val rotation = Rotation((0..4).toMutableList(), 0)
        //Act
        //Assert
        assertEquals(1, rotation.peekNext())
        assertEquals(1, rotation.peekNext())
        assertEquals(1, rotation.peekNext())
    }

    @Test
    fun `should return next item after x steps without rotating`() {
        //Arrange
        val rotation = Rotation((0..4).toMutableList(), 0)
        //Act
        //Assert
        assertEquals(2, rotation.peekNext(2))
        assertEquals(2, rotation.peekNext(2))
        assertEquals(2, rotation.peekNext(2))
    }

    @Test
    fun `should remove matching item`() {
        //Arrange
        val rotation = Rotation((1..5).toMutableList(), 0)
        //Act
        rotation.remove { it == 2 }
        //Assert
        assertEquals(rotation.items.count(), 4)
        assertFalse(rotation.items.contains(2), "Item is still in collection")
    }
}