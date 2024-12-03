package day04

import readInput
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 1L)
    //check(part2(testInput).also(::println) == 1L)

    val input = readInput("Day04")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
