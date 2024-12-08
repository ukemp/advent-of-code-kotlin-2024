package day08

import CharGrid
import Coordinate
import readLines
import kotlin.time.measureTime

fun main() {

    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        val antinodes = mutableSetOf<Coordinate>()
        val uniqueFrequencies = mutableSetOf<Char>()
        grid.forEach { g, p, c -> if (c != '.') uniqueFrequencies.add(c) }

        for (frequency in uniqueFrequencies) {
            val positions = buildList {
                grid.forEach { g, p, c -> if (c == frequency) add(p) }
            }
            positions.forEachIndexed { index, p1 ->
                for (i in positions.indices) {
                    if (i == index) {
                        continue
                    }
                    val p2 = positions[i]
                    val d = p2 - p1
                    if (grid.isInside(p1 - d)) {
                        antinodes.add(p1 - d)
                    }
                    if (grid.isInside(p2 + d)) {
                        antinodes.add(p2 + d)
                    }
                }
            }
        }
        return antinodes.size.toLong()
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)
        val antinodes = mutableSetOf<Coordinate>()
        val uniqueFrequencies = mutableSetOf<Char>()
        grid.forEach { g, p, c -> if (c != '.') uniqueFrequencies.add(c) }

        for (frequency in uniqueFrequencies) {
            val positions = buildList {
                grid.forEach { g, p, c -> if (c == frequency) add(p) }
            }
            antinodes.addAll(positions)
            positions.forEachIndexed { index, p1 ->
                for (i in positions.indices) {
                    if (i == index) {
                        continue
                    }
                    val p2 = positions[i]
                    val dx = p2 - p1
                    var d = dx

                    while (grid.isInside(p1 - d)) {
                        antinodes.add(p1 - d)
                        d -= dx
                    }

                    d = dx
                    while (grid.isInside(p2 + d)) {
                        antinodes.add(p2 + d)
                        d += dx
                    }
                }
            }
        }
        return antinodes.size.toLong()
    }

    val testInput = readLines("Day08_test")
    check(part1(testInput).also(::println) == 14L)
    check(part2(testInput).also(::println) == 34L)

    val input = readLines("Day08")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
