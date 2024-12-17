package util

sealed class Direction(x: Int, y: Int) : Coordinate(x, y) {

    object UP : Direction(0, -1) {
        override fun toString(): String {
            return "UP"
        }
    }

    object DOWN : Direction(0, 1) {
        override fun toString(): String {
            return "DOWN"
        }
    }

    object LEFT : Direction(-1, 0) {
        override fun toString(): String {
            return "LEFT"
        }
    }

    object RIGHT : Direction(1, 0) {
        override fun toString(): String {
            return "RIGHT"
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    fun turnLeft(): Direction {
        return when (this) {
            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }
    }

    fun revert(): Direction {
        return when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    }

    companion object {

        fun from(c: Char): Direction {
            return when (c) {
                '<' -> LEFT
                '^' -> UP
                '>' -> RIGHT
                'v' -> DOWN
                else -> error("Unexpected direction: $c (valid chars are < ^ > v)")
            }
        }
    }
}