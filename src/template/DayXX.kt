package template

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readInput("DayXX_test")
    check(part1(testInput).also(::println) == 1L)
    //check(part2(testInput).also(::println) == 1L)

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}
