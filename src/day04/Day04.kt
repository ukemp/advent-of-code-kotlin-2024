package day04

import CharGrid
import Coordinate
import readInput
import kotlin.time.measureTime

fun main() {

    val right = Coordinate(3, 0)
    val left = Coordinate(-3, 0)
    val bottom = Coordinate(0, 3)
    val up = Coordinate(0, -3)
    val directions = listOf<Coordinate>(right, left, bottom, up, up + left, up + right, bottom + right, bottom + left)

    fun CharGrid.hasXmas(x: Int, y: Int): Boolean {
        return if (this[x, y] != 'A') {
            false
        } else {
            (this[x - 1, y - 1] == 'M' && this[x + 1, y - 1] == 'S' && this[x - 1, y + 1] == 'M' && this[x + 1, y + 1] == 'S') ||
                    (this[x - 1, y - 1] == 'S' && this[x + 1, y - 1] == 'M' && this[x - 1, y + 1] == 'S' && this[x + 1, y + 1] == 'M') ||
                    (this[x - 1, y - 1] == 'M' && this[x + 1, y - 1] == 'M' && this[x - 1, y + 1] == 'S' && this[x + 1, y + 1] == 'S') ||
                    (this[x - 1, y - 1] == 'S' && this[x + 1, y - 1] == 'S' && this[x - 1, y + 1] == 'M' && this[x + 1, y + 1] == 'M')
        }
    }

    fun part1(input: List<String>): Long {
        var sum = 0L

        CharGrid(input).forEach { grid, position, c ->
            directions.forEach {
                if (grid.wordTo(position, it) == "XMAS") sum++
            }
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        CharGrid(input).forEach { grid, position, _ ->
            if (grid.hasXmas(position.x, position.y)) sum++
        }

        return sum
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 18L)
    check(part2(testInput).also(::println) == 9L)

    val input = readInput("Day04")

    measureTime {
        part1(input).also(::println)
    }.also { println("Solved part 1 in $it") }

    measureTime {
        part2(input).also(::println)
    }.also { println("Solved part 2 in $it") }
}
