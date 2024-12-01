package day01

import readInput
import kotlin.math.absoluteValue
import kotlin.to

fun main() {

    fun readInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        return input.fold(mutableListOf<Int>() to mutableListOf<Int>()) { ids, line ->
            ids.first.add(line.substringBefore(' ').toInt())
            ids.second.add(line.substringAfter(' ').trim().toInt())
            ids
        }
    }

    fun part1(input: List<String>): Long {
        val (left, right) = readInput(input)
        left.sort()
        right.sort()

        return left.mapIndexed { index, value -> (value - right[index]).absoluteValue }.sum().toLong()
    }

    fun part2(input: List<String>): Long {
        val (left, right) = readInput(input)
        left.sort()
        right.sort()

        var startIndex = 0
        return left.sumOf { id ->
            var count = 0
            for (index in startIndex..<right.size) {
                if (right[index] == id) {
                    count++
                } else if (right[index] > id) {
                    startIndex = index
                    break
                }
            }
            count * id
        }.toLong()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput).also(::println) == 11L)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
