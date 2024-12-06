package day06

import CharGrid
import Coordinate
import readLines
import kotlin.time.measureTime

data class Step(val position: Coordinate, val direction: Coordinate) {

    fun move() = Step(position + direction, direction)

    fun turnRight() = Step(position, direction.turnRight())
}

fun CharGrid.nextPositionFor(current: Step): Step {
    var next = current.move()

    if (get(next.position, ' ') == '#') {
        next = current.turnRight().move()
        if (get(next.position, ' ') == '#') {
            next = current.turnRight().turnRight().move()
        }
    }
    return next
}

fun walk(grid: CharGrid, first: Step): Set<Step> {
    var current = first
    val visited = mutableSetOf<Step>()

    while (grid.isInside(current.position)) {
        visited.add(current)
        current = grid.nextPositionFor(current)
        if (current in visited) return emptySet()
    }
    return visited
}

fun main() {

    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        var first = Step(grid.indexOf('^'), Coordinate.UP)
        return walk(grid, first).map { it.position }.toSet().size.toLong()
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)
        var first = Step(grid.indexOf('^'), Coordinate.UP)
        val visited = walk(grid, first).map { it.position }.toSet()

        return (visited - first.position).count { obstacle ->
            walk(CharGrid(input).set(obstacle, '#'), first).isEmpty()
        }.toLong()
    }

    val testInput = readLines("Day06_test")
    check(part1(testInput).also(::println) == 41L)
    check(part2(testInput).also(::println) == 6L)

    val input = readLines("Day06")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
