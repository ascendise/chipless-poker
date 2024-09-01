package ch.ascendise.pokertracker.rotation

internal object Rotations {

    /**
     * Adds two values. If the sum is more than max, the value resets at 0
     *
     * Example: left = 2, right = 5, max = 4, start = -1
     * Result = 2
     *
     * @param left
     * @param right
     * @param max
     */
    fun rotatingAdd(left: Int, right: Int, max: Int, start: Int = 0): Int {
        if(max < start) throw IllegalArgumentException("max cannot be less than start")
        return (left + right) % (max + 1) + start
    }

    /**
     * Subtracts two values. If the result is less than 0, the value resets at the max again
     *
     * Example: left = 2, right = 5, max = 4, start = -1
     * Result = 2
     *
     * @param left
     * @param right
     * @param max
     * @param start
     */
    fun rotatingSub(left: Int, right: Int, max: Int, start: Int = 0): Int {
        if(max < start) throw IllegalArgumentException("max cannot be less than start")
        val result = left - right
        if(result >= start)
            return result
        return max - (((result + 1) * -1) % (max + 1))
    }
}