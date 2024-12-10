package day04

import CharGrid
import Coordinate
import readLines
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
        val grid = CharGrid(input)

        return grid.coordinates().sumOf { position ->
            directions.sumOf {
                if (grid.wordTo(position, it) == "XMAS") 1L else 0L
            }
        }
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)

        return grid.coordinates().sumOf { position ->
            if (grid.hasXmas(position.x, position.y)) 1L else 0L
        }
    }

    val testInput = readLines("Day04_test")
    check(part1(testInput).also(::println) == 18L)
    check(part2(testInput).also(::println) == 9L)

    val input = readLines("Day04")

    measureTime {
        part1(input).also(::println)
    }.also { println("Solved part 1 in $it") }

    measureTime {
        part2(input).also(::println)
    }.also { println("Solved part 2 in $it") }
}
