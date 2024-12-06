@file:Suppress("unused")

import kotlin.math.absoluteValue
import kotlin.math.sign

@Suppress("NOTHING_TO_INLINE")
inline fun Coordinate(x: Int, y: Int) = Coordinate(packInts(x, y))

@Suppress("NOTHING_TO_INLINE")
inline fun Coordinate(str: String, delimiter: Char = ','): Coordinate {
    return Coordinate(
        packInts(
            str.substringBefore(delimiter).trim().toInt(),
            str.substringAfter(delimiter).trim().toInt()
        )
    )
}

@JvmInline
@Suppress("MemberVisibilityCanBePrivate")
value class Coordinate(private val packedValue: Long) {

    val x: Int
        get() = (packedValue shr 32).toInt()

    val y: Int
        get() = (packedValue and 0xFFFFFFFF).toInt()

    operator fun plus(other: Coordinate) = Coordinate(packInts(x + other.x, y + other.y))

    operator fun minus(other: Coordinate) = Coordinate(packInts(x - other.x, y - other.y))

    operator fun component1(): Int = x

    operator fun component2(): Int = y

    operator fun rangeTo(other: Coordinate): Iterator<Coordinate> {
        val diff = other - this
        val dx = diff.x.sign
        val dy = diff.y.sign

        if (dx == 0 || dy == 0 || diff.x.absoluteValue == diff.y.absoluteValue) {
            val d = Coordinate(dx, dy)
            return buildList {
                add(this@Coordinate)
                while (last() != other) {
                    add(last() + d)
                }
            }.iterator()
        } else {
            throw IllegalArgumentException("Cannot create $this..$other (only vertical, horizontal or diagonal ranges are supported")
        }
    }

    val axialNeighbors: List<Coordinate>
        get() = listOf(UP, DOWN, LEFT, RIGHT).map { it + this }

    val diagonalNeighbors: List<Coordinate>
        get() = listOf(UP + LEFT, UP + RIGHT, DOWN + LEFT, DOWN + RIGHT).map { it + this }

    val neighbors: List<Coordinate>
        get() = axialNeighbors + diagonalNeighbors


    fun manhattenDistanceTo(other: Coordinate): Long {
        return (x - other.x).absoluteValue.toLong() + (y - other.y).absoluteValue
    }

    fun turnRight(): Coordinate {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> error("$this is not a direction")
        }
    }

    override fun toString(): String {
        return when (this) {
            UP -> "[$x, $y] (up)"
            RIGHT -> "[$x, $y] (right)"
            DOWN -> "[$x, $y] (down)"
            LEFT -> "[$x, $y] (left)"
            else -> "[$x, $y]"
        }
    }

    companion object {
        val ZERO = Coordinate(0, 0)
        val UP = Coordinate(0, -1)
        val DOWN = Coordinate(0, 1)
        val LEFT = Coordinate(-1, 0)
        val RIGHT = Coordinate(1, 0)
    }
}

fun packInts(x: Int, y: Int): Long {
    return (x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFF)
}
