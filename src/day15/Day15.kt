package day15

import CharGrid
import Coordinate
import Direction
import readLines
import kotlin.time.measureTime

fun main() {

    fun readInput(input: List<String>): Pair<CharGrid, List<Direction>> {
        val split = input.indexOf("")
        val grid = CharGrid(input.subList(0, split))
        val directions = input.subList(split + 1, input.size).flatMap { it.map { Direction.from(it) } }

        return grid to directions
    }

    fun CharGrid.makeLarger(): CharGrid {
        return CharGrid(buildList<String> {
            for (y in verticalIndices) {
                add(buildString {
                    for (x in horizontalIndices) {
                        when (val c = this@makeLarger[x, y, null]!!) {
                            '#' -> append("##")
                            'O' -> append("[]")
                            '.' -> append("..")
                            '@' -> append("@.")
                            else -> error("Unexpected $c")
                        }
                    }
                })
            }
        })
    }

    fun CharGrid.canMovePart1(position: Coordinate, direction: Direction): Boolean {
        val test = this[position + direction, null]!!
        return if (test == '.') {
            true
        } else if (test == '#') {
            false
        } else {
            canMovePart1(position + direction, direction)
        }
    }

    fun CharGrid.movePart1(position: Coordinate, direction: Direction) {
        val current = this[position, null]!!
        val next = this[position + direction, null]!!
        if (next == 'O') {
            movePart1(position + direction, direction)
        }
        this[position + direction] = current
        this[position] = '.'
    }

    fun part1(input: List<String>): Long {
        val (grid, directions) = readInput(input)
        var current = grid.indexOf('@')
        println("Starting at: $current with ${directions.size} directions")
        directions.forEach { direction ->
            if (grid.canMovePart1(current, direction)) {
                grid.movePart1(current, direction)
                current += direction
            }
        }
        return grid.filter { position, c -> c == 'O' }.sumOf { it.y * 100L + it.x }
    }

    fun part2(input: List<String>): Long {
        val (small, directions) = readInput(input)
        val grid = small.makeLarger()
        println(grid)
        return input.size.toLong()
    }

    val testInput = readLines("Day15_test")
    //check(part1(testInput).also(::println) == 10092L)
    check(part2(testInput).also(::println) == 1L)

    val input = readLines("Day15")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
