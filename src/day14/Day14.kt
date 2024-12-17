package day14

import util.Coordinate
import util.readLines
import kotlin.time.measureTime

data class Robot(val position: Coordinate, val velocity: Coordinate)


class Bathroom(val width: Int, val height: Int, val robots: MutableList<Robot>) {

    fun move(steps: Int) {
        robots.forEachIndexed { index, robot ->
            var x = (robot.position.x + (steps * robot.velocity.x)) % width
            var y = (robot.position.y + (steps * robot.velocity.y)) % height
            if (x < 0) {
                x = width + x
            }
            if (y < 0) {
                y = height + y
            }
            robots[index] = robot.copy(position = Coordinate(x, y))
        }
    }

    fun safetyFactor(): Long {
        val q1 = countIn(0..(width / 2) - 1, 0..(height / 2) - 1)
        val q2 = countIn(((width / 2) + 1)..width, 0..(height / 2) - 1)
        val q3 = countIn(((width / 2) + 1)..width, ((height / 2) + 1)..height)
        val q4 = countIn(0..(width / 2) - 1, ((height / 2) + 1)..height)

        return (q1 * q2 * q3 * q4).toLong()
    }

    private fun countIn(horizontal: IntRange, vertical: IntRange): Int {
        return robots.count { robot ->
            robot.position.x in horizontal && robot.position.y in vertical
        }
    }

    fun hasEasterEgg(): Boolean {
        // Easter egg is when all robots are on a unique position:
        return robots.map { it.position }.toSet().size == robots.size
    }

    override fun toString(): String {
        val positions = robots.map { it.position }.toSet()

        return buildString {
            for (y in 0..<height) {
                for (x in 0..<width) {
                    if (Coordinate(x, y) in positions) append('X') else append('·')
                }
                if (y < height - 1) append("\n")
            }
        }
    }

    companion object {
        fun from(input: List<String>, width: Int, height: Int): Bathroom {
            val robots = input.map { line ->
                val (p, v) = line.split(" ")
                Robot(Coordinate(p.substringAfter('=')), Coordinate(v.substringAfter('=')))
            }
            return Bathroom(width, height, robots.toMutableList())
        }
    }
}

fun main() {
    fun part1(input: List<String>, width: Int, height: Int): Long {
        val bathroom = Bathroom.from(input, width, height)
        bathroom.move(100)

        return bathroom.safetyFactor()
    }

    fun part2(input: List<String>, width: Int, height: Int): Long {
        val bathroom = Bathroom.from(input, width, height)
        for (i in 0..Int.MAX_VALUE) {
            if (bathroom.hasEasterEgg()) {
                println(bathroom)
                return i.toLong()
            }
            bathroom.move(1)
        }
        return -1
    }

    val testInput = readLines("Day14_test")
    check(part1(testInput, 11, 7).also(::println) == 12L)

    val input = readLines("Day14")

    measureTime {
        println(part1(input, 101, 103))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input, 101, 103))
    }.also { println("Solved part 2 in $it") }
}
