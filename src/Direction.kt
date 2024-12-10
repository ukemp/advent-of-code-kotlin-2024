class Direction private constructor(x: Int, y: Int) : Coordinate(x, y) {

    fun turnRight(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> error("$this is not a direction")
        }
    }

    companion object {
        val UP = Direction(0, -1)
        val DOWN = Direction(0, 1)
        val LEFT = Direction(-1, 0)
        val RIGHT = Direction(1, 0)
    }
}