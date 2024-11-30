package template

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput).also(::println) == 1L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
