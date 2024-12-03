package day03

import readInput
import kotlin.time.measureTime

val mul = """mul\((\d+),(\d+)\)""".toRegex()

fun instructionsOf(str: String): Long {
    return mul.findAll(str).sumOf { matcher ->
        val (a, b) = matcher.destructured
        a.toLong() * b.toLong()
    }
}

fun part1(input: List<String>): Long {
    return input.sumOf { line -> instructionsOf(line) }
}

fun part2IndexOf(input: List<String>): Long {
    val startMarker = "do()"
    val endMarker = "don't()"
    var enabled = true

    return input.sumOf { line ->
        var index = 0
        var sum = 0L

        while (index < line.length) {
            if (enabled) {
                val end = line.indexOf(endMarker, index)
                if (end == -1) {
                    sum += instructionsOf(line.substring(index))
                    break
                } else {
                    sum += instructionsOf(line.substring(index, end))
                    enabled = false
                    index = end + endMarker.length
                }
            } else {
                val start = line.indexOf(startMarker, index)
                if (start == -1) {
                    break
                } else {
                    enabled = true
                    index = start + startMarker.length
                }
            }
        }
        sum
    }
}

fun part2AllRegex(input: List<String>): Long {
    val regex = """mul\((\d+),(\d+)\)|do\(\)|don't\(\)""".toRegex()
    var enabled = true

    return regex.findAll(input.joinToString("")).sumOf { matcher ->
        if (matcher.value.startsWith("mul")) {
            if (enabled) {
                val (a, b) = matcher.destructured
                return@sumOf a.toLong() * b.toLong()
            }
        } else if (matcher.value == "don't()") {
            enabled = false
        } else {
            enabled = true
        }
        0L
    }
}

fun main() {
    val testInput = readInput("Day03_test")
    check(part1(testInput).also(::println) == 161L)
    check(part2IndexOf(testInput).also(::println) == 48L)

    val input = readInput("Day03")

    measureTime {
        part1(input).also(::println)
    }.also { println("Solved part 1 in $it") }

    measureTime {
        part2IndexOf(input).also(::println)
        part2AllRegex(input).also(::println)
    }.also { println("Solved part 2 in $it") }
}
