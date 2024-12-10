package day10

import CharGrid
import Coordinate
import readLines
import kotlin.time.measureTime

fun main() {

    fun CharGrid.walk(position: Coordinate, score: MutableCollection<Coordinate>) {
        val current = get(position, null)!!.digitToInt()
        if (current == 9) {
            score.add(position)
        } else {
            position.axialNeighbors.filter { neighbour ->
                (neighbour in this) && (get(neighbour, null)!!.digitToInt() == current + 1)
            }.forEach {
                walk(it, score)
            }
        }
    }

    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        return grid.filter { _, c -> c.digitToInt() == 0 }.sumOf { coordinate ->
            val score = mutableSetOf<Coordinate>()
            grid.walk(coordinate, score)
            score.size.toLong()
        }
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)
        return grid.filter { _, c -> c.digitToInt() == 0 }.sumOf { coordinate ->
            val score = mutableListOf<Coordinate>()
            grid.walk(coordinate, score)
            score.size.toLong()
        }
    }

    val testInput = readLines("Day10_test")
    check(part1(testInput).also(::println) == 36L)
    check(part2(testInput).also(::println) == 81L)

    val input = readLines("Day10")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
