package day19

import util.readLines
import kotlin.time.measureTime

fun isDesignPossible(design: String, towels: List<String>): Boolean {
    return if (design.isEmpty()) {
        true
    } else {
        towels.any { towel ->
            design.startsWith(towel) && isDesignPossible(design.substring(towel.length), towels)
        }
    }
}

fun allPossibleDesigns(design: String, towels: List<String>, cache: MutableMap<String, Long>): Long {
    return cache.getOrPut(design) {
        if (design.isEmpty()) {
            1
        } else {
            towels.filter { design.startsWith(it) }
                .sumOf { towel -> allPossibleDesigns(design.drop(towel.length), towels, cache) }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val towels = input[0].split(", ")
        return input.takeLast(input.size - 2).count {
            isDesignPossible(it, towels)
        }.toLong()
    }

    fun part2(input: List<String>): Long {
        val towels = input[0].split(", ")
        return input.takeLast(input.size - 2).sumOf {
            allPossibleDesigns(it, towels, mutableMapOf())
        }
    }

    val testInput = readLines("Day19_test")
    check(part1(testInput).also(::println) == 6L)
    check(part2(testInput).also(::println) == 16L)

    val input = readLines("Day19")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
