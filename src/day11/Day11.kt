package day11

import util.mapToLong
import util.readLines
import java.math.BigInteger
import kotlin.math.ln
import kotlin.time.measureTime

fun main() {

    val factor = ln(2f) / ln(10f);

    var max = BigInteger.ZERO

    fun BigInteger.digitCount(): Int {
        val digitCount = (factor * bitLength() + 1).toInt();
        if (BigInteger.TEN.pow(digitCount - 1) > this) {
            return digitCount - 1;
        }
        return digitCount;
    }

    fun solve(step: Int, end: Int, stone: Long, preComputed: Map<Long, BigInteger>): BigInteger {
        if (step == end) {
            return BigInteger.ONE
        }
        if (stone == 0L && preComputed.containsKey(stone)) {
            return preComputed[stone]!!
        }

        val str = stone.toString()
        val digits = str.length
        return if (stone == 0L) {
            solve(step + 1, end, 1L, preComputed)
        } else if (digits % 2 == 0) {
            val half = digits / 2
            val left = str.substring(0, half).toLong()
            val right = str.substring(half).toLong()
            solve(step + 1, end, left, preComputed) + solve(step + 1, end, right, preComputed)
        } else {
            solve(step + 1, end, stone * 2024L, preComputed)
        }
    }

    fun part1(input: List<String>): BigInteger {
        val preComputed = mutableMapOf<Long, BigInteger>()
        for (step in 1..40) {
            val x = solve(0, step, 0, emptyMap())
            preComputed[step.toLong()] = x
            println("------------------------ $step $x")
        }
        var stones = input[0].split(" ").mapToLong()
        return stones.sumOf { stone ->
            //solve(0, 25, BigInteger.valueOf(stone))
            solve(0, 25, stone, preComputed)
        }
    }

    fun part2(input: List<String>): BigInteger {
        val preComputed = mutableMapOf<Long, BigInteger>()
        for (step in 1..40) {
            val x = solve(0, step, 0, emptyMap())
            preComputed[step.toLong()] = x
        }
        var stones = input[0].split(" ").mapToLong()
        return stones.sumOf { stone ->
            //solve(0, 75, BigInteger.valueOf(stone))
            solve(0, 75, stone, preComputed)
        }
    }

    val testInput = readLines("Day11_test")
    check(part1(testInput).also(::println) == BigInteger.valueOf(55312L))
    //check(part2(testInput).also(::println) == 1L)

    val input = readLines("Day11")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
