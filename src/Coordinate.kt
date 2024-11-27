@file:Suppress("unused")

import kotlin.math.absoluteValue

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

    operator fun component1(): Int = x

    operator fun component2(): Int = y

    operator fun rangeTo(other: Coordinate): Iterator<Coordinate> {
        return if (this.x == other.x) {
            if (this.y < other.y) {
                (this.y..other.y).map { y -> Coordinate(this.x, y) }.iterator()
            } else {
                (this.y downTo other.y).map { y -> Coordinate(this.x, y) }.iterator()
            }
        } else if (this.y == other.y) {
            if (this.x < other.x) {
                (this.x..other.x).map { x -> Coordinate(x, this.y) }.iterator()
            } else {
                (this.x downTo other.x).map { x -> Coordinate(x, this.y) }.iterator()
            }
        } else {
            throw IllegalArgumentException("For a coordinate range at least one dimension must be identical: $this .. $other")
        }
    }

    fun copy(x: Int = this.x, y: Int = this.y) = Coordinate(x, y)

    fun moveBy(dx: Int = 0, dy: Int = 0) = Coordinate(x + dx, y + dy)

    fun distanceTo(other: Coordinate): Long {
        return ((this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue).toLong()
    }

    fun neighbours(xRange: IntRange = 0..Int.MAX_VALUE, yRange: IntRange = 0..Int.MAX_VALUE): List<Coordinate> {
        return buildList {
            if ((y - 1) in yRange) {
                add(moveBy(dy = -1))
            }
            if ((y - 1) in yRange && (x + 1) in xRange) {
                add(moveBy(dy = -1, dx = 1))
            }
            if ((x + 1) in xRange) {
                add(moveBy(dx = 1))
            }
            if ((y + 1) in yRange && (x + 1) in xRange) {
                add(moveBy(dy = 1, dx = 1))
            }
            if ((y + 1) in yRange) {
                add(moveBy(dy = 1))
            }
            if ((y + 1) in yRange && (x - 1) in xRange) {
                add(moveBy(dy = 1, dx = -1))
            }
            if ((x - 1) in xRange) {
                add(moveBy(dx = -1))
            }
            if ((y - 1) in yRange && (x - 1) in xRange) {
                add(moveBy(dy = -1, dx = -1))
            }
        }
    }

    override fun toString(): String {
        return "[$x, $y]"
    }

    companion object {

        val Zero = Coordinate(0, 0)
    }
}

fun packInts(x: Int, y: Int): Long {
    return (x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFF)
}
