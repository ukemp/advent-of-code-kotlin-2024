@file:Suppress("unused")

package util

import kotlin.math.absoluteValue
import kotlin.math.sign

@Suppress("NOTHING_TO_INLINE")
inline fun Coordinate(str: String, delimiter: Char = ','): Coordinate {
    return Coordinate(str.substringBefore(delimiter).trim().toInt(), str.substringAfter(delimiter).trim().toInt())
}

@Suppress("MemberVisibilityCanBePrivate")
open class Coordinate(val x: Int, val y: Int) {

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)

    operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)

    operator fun times(other: Coordinate) = Coordinate(x * other.x, y * other.y)

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

    val axialNeighbors: Sequence<Coordinate>
        get() = sequence<Coordinate> {
            yield(Direction.UP + this@Coordinate)
            yield(Direction.DOWN + this@Coordinate)
            yield(Direction.LEFT + this@Coordinate)
            yield(Direction.RIGHT + this@Coordinate)
        }

    val diagonalNeighbors: Sequence<Coordinate>
        get() = sequence<Coordinate> {
            yield(Direction.UP + Direction.LEFT + this@Coordinate)
            yield(Direction.UP + Direction.RIGHT + this@Coordinate)
            yield(Direction.DOWN + Direction.LEFT + this@Coordinate)
            yield(Direction.DOWN + Direction.RIGHT + this@Coordinate)
        }

    val neighbors: Sequence<Coordinate>
        get() = axialNeighbors + diagonalNeighbors


    fun manhattanDistanceTo(other: Coordinate): Long {
        return (x - other.x).absoluteValue.toLong() + (y - other.y).absoluteValue
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate) return false

        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        return x xor y
    }

    override fun toString(): String {
        return "[$x, $y]"
    }

    companion object {
        val ORIGIN = Coordinate(0, 0)
    }
}
