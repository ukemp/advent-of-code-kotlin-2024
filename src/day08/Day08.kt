package day08

import CharGrid
import Coordinate
import readLines
import kotlin.time.measureTime

fun main() {

    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        val antinodes = mutableSetOf<Coordinate>()
        val uniqueFrequencies = grid.all { p, c -> c != '.' }

        for (frequency in uniqueFrequencies) {
            val positions = grid.filter { _, c -> c == frequency }.toList()
            positions.forEachIndexed { index, p1 ->
                for (i in positions.indices) {
                    if (i == index) {
                        continue
                    }
                    val p2 = positions[i]
                    val d = p2 - p1
                    if ((p1 - d) in grid) {
                        antinodes.add(p1 - d)
                    }
                    if ((p2 + d) in grid) {
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
        val uniqueFrequencies = grid.all { p, c -> c != '.' }

        for (frequency in uniqueFrequencies) {
            val positions = grid.filter { _, c -> c == frequency }.toList()
            antinodes.addAll(positions)
            positions.forEachIndexed { index, p1 ->
                for (i in positions.indices) {
                    if (i == index) {
                        continue
                    }
                    val p2 = positions[i]
                    val dx = p2 - p1
                    var d = dx

                    while ((p1 - d) in grid) {
                        antinodes.add(p1 - d)
                        d -= dx
                    }

                    d = dx
                    while ((p2 + d) in grid) {
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
