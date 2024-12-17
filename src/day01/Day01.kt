package day01

import util.readLines
import kotlin.math.absoluteValue
import kotlin.to

fun main() {

    fun readSortedInput(input: List<String>): Pair<MutableList<Long>, MutableList<Long>> {
        return input.fold(mutableListOf<Long>() to mutableListOf<Long>()) { ids, line ->
            ids.first.add(line.substringBefore(' ').toLong())
            ids.second.add(line.substringAfterLast(' ').toLong())
            ids
        }.apply {
            first.sort()
            second.sort()
        }
    }

    fun part1(input: List<String>): Long {
        val (left, right) = readSortedInput(input)

        return left.zip(right) { l, r -> (l - r).absoluteValue }.sum()
    }

    fun part2(input: List<String>): Long {
        val (left, right) = readSortedInput(input)

        var startIndex = 0
        return left.sumOf { id ->
            // count = right.count { it == id } is more than 4 some slower
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
        }
    }

    val testInput = readLines("Day01_test")
    check(part1(testInput).also(::println) == 11L)

    val input = readLines("Day01")
    println(part1(input))
    println(part2(input))
}
