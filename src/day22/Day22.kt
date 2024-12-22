package day22

import util.mapToLong
import util.readLines
import kotlin.time.measureTime

fun evolve(secret: Long): Long {
    val step1 = ((secret * 64) xor secret) % 16777216L
    val step2 = ((step1 / 32) xor step1) % 16777216L
    val step3 = ((step2 * 2048) xor step2) % 16777216L
    return step3
}

fun main() {
    fun part1(input: List<String>): Long {
        return input.mapToLong().sumOf { secret ->
            var evolved = secret
            repeat(2000) {
                evolved = evolve(evolved)
            }
            evolved
        }
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day22_test")
    check(part1(testInput).also(::println) == 37327623L)
    //check(part2(testInput).also(::println) == 1L)

    val input = readLines("Day22")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
