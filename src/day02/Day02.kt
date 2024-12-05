package day02

import readLines
import kotlin.math.absoluteValue

fun main() {
    fun isSafe(values: List<Int>): Boolean {
        var isSafe = true
        val isIncreasing = values[0] < values[1]

        for (index in 0..<values.size - 1) {
            val left = values[index]
            val right = values[index + 1]
            val diff = (right - left).absoluteValue
            if ((diff == 0 || diff > 3) ||
                (isIncreasing && left >= right) ||
                (!isIncreasing && right >= left)
            ) {
                isSafe = false
                break
            }
        }
        return isSafe
    }

    fun part1(input: List<String>): Long {
        return input.count { line ->
            isSafe(line.split(" ").map { it.toInt() })
        }.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.count { line ->
            val values = line.split(" ").map { it.toInt() }
            if (isSafe(values)) {
                true
            } else {
                var isSafe = false
                for (index in 0..<values.size) {
                    val removed = values.toMutableList()
                    removed.removeAt(index)
                    if (isSafe(removed)) {
                        isSafe = true
                        break
                    }
                }
                isSafe
            }
        }.toLong()
    }

    val testInput = readLines("Day02_test")
    check(part1(testInput).also(::println) == 2L)
    check(part2(testInput).also(::println) == 4L)

    val input = readLines("Day02")
    println(part1(input))
    println(part2(input))
}
