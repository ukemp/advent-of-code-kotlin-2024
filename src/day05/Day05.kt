package day05

import mapToInt
import readInput
import kotlin.time.measureTime

fun main() {

    fun parseRules(input: List<String>): List<Pair<Int, Int>> {
        return input.mapNotNull { line ->
            if (line.contains("|")) {
                line.substringBefore('|').toInt() to line.substringAfter('|').toInt()
            } else {
                null
            }
        }
    }

    fun parsePages(input: List<String>): List<List<Int>> {
        return input.mapNotNull { line ->
            if (line.contains(",")) {
                line.split(",").mapToInt()
            } else {
                null
            }
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

    fun MutableList<Int>.swap(i1: Int, i2: Int) {
        val v1 = get(i1)
        set(i1, get(i2))
        set(i2, v1)
    }

    fun part1(input: List<String>): Long {
        val rules = parseRules(input)
        val pagesList = parsePages(input)

        return pagesList.sumOf { pages ->
            if (isOrdered(pages, rules)) pages[pages.size / 2].toLong() else 0L
        }
    }

    fun part2(input: List<String>): Long {
        val rules = parseRules(input)
        val pagesList = parsePages(input)

        return pagesList.sumOf {
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

    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == 143L)
    check(part2(testInput).also(::println) == 123L)

    val input = readInput("Day05")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
