package day05

import mapToInt
import readText
import swap
import kotlin.time.measureTime

fun main() {

    fun parseInput(input: String): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val (firstPart, secondPart) = input.split("\n\n")

        return firstPart.lines().map { line ->
            line.substringBefore('|').toInt() to line.substringAfter('|').toInt()
        } to secondPart.lines().map { line ->
            line.split(",").mapToInt()
        }
    }

    fun indexesOf(pages: List<Int>, rule: Pair<Int, Int>) =
        pages.indexOf(rule.first) to pages.indexOf(rule.second)

    fun isOrdered(pages: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        return rules.all { rule ->
            val (i1, i2) = indexesOf(pages, rule)
            !(i1 != -1 && i2 != -1 && i1 >= i2)
        }
    }

    fun part1(input: String): Long {
        val (rules, updates) = parseInput(input)

        return updates.sumOf { pages ->
            if (isOrdered(pages, rules)) pages[pages.size / 2].toLong() else 0L
        }
    }

    fun part2(input: String): Long {
        val (rules, updates) = parseInput(input)

        return updates.sumOf {
            val pages = it.toMutableList()
            if (isOrdered(pages, rules)) {
                0L
            } else {
                do {
                    rules.forEach { rule ->
                        val (i1, i2) = indexesOf(pages, rule)
                        if (i1 != -1 && i2 != -1) {
                            if (i1 >= i2) {
                                pages.swap(i1, i2)
                            }
                        }
                    }
                } while (!isOrdered(pages, rules))

                pages[pages.size / 2].toLong()
            }
        }
    }

    val testInput = readText("Day05_test")
    check(part1(testInput).also(::println) == 143L)
    check(part2(testInput).also(::println) == 123L)

    val input = readText("Day05")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
