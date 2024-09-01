package ch.ascendise.pokertracker.rotation

/**
 * Rotating iterator around the given collection of items.
 * Starting at the given index, the iterator returns the next item in the collection.
 * At the end of the collection, the iterator starts at index 0 again.
 *
 * Note: Given the nature of this class, any for each loops should have an additional condition
 * to exit the loop, as else the loop will run forever
 *
 * @param T
 * @property items
 */
internal class Rotation<T>(items: MutableList<T>, startingIndex: Int = items.count() - 1) : Iterator<T> {

    private val _items: MutableList<T> = items
    val items: List<T>
        get() { return _items }


    private var currentIndex: Int = startingIndex
        set(value) {
            if(isOutOfBounds(value))
                throw IndexOutOfBoundsException()
            field = value
        }

    private fun isOutOfBounds(index: Int)
        = index < 0 || index > items.count() - 1

    init {
        if(items.isEmpty())
            throw IllegalArgumentException("items cannot be empty!")
        if(startingIndex < 0 || startingIndex > items.count() - 1) {
            throw IndexOutOfBoundsException()
        }
    }

    /**
     * Rotation is infinite so has next always returns true as on the last member, the next member
     * is the first member
     *
     * @return true
     */
    override fun hasNext(): Boolean = true

    /**
     * Returns the next member in rotation
     *
     * @return
     */
    override fun next(): T
        = rotateIndex().let { items[currentIndex] }

    /**
     * Sets the next element to be returned
     *
     */
    fun setNext(index: Int) {
        currentIndex = Rotations.rotatingSub(index, 1, items.count() - 1)
    }


    /**
     * Return the next member in rotation without moving the cursor
     *
     * @return
     */
    fun peekNext(steps: Int = 1): T
        = items[Rotations.rotatingAdd(currentIndex, steps, items.count() - 1)]


    private fun rotateIndex() {
        currentIndex = Rotations.rotatingAdd(currentIndex, 1, items.count() - 1)
    }

    /**
     * Returns the next member after x steps in rotation
     *
     *
     * @param steps
     * @return Equivalent to the result of the x-th call of next()
     */
    fun next(steps: Int = 1): T {
        currentIndex = Rotations.rotatingAdd(currentIndex, steps, items.count() - 1)
        return items[currentIndex]
    }

    fun remove(predicate: (T) -> Boolean) {
        val count = _items.count()
       _items.removeAll { predicate(it) }
        val newCount = _items.count()
        setNext(Rotations.rotatingSub(currentIndex, count - newCount, newCount))
    }
}