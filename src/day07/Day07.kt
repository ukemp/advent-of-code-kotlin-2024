package day07

import mapToLong
import readLines
import kotlin.time.measureTime

data class Equation(val result: Long, val operands: List<Long>) {

    init {
        check(result > 0) { "$result: $operands contains non positive result" }
        check(operands.all { it > 0 }) { "$result: $operands contains non positive operators" }
    }

    fun matches(): Boolean {
        for (operation in 0..1.shl(operands.size)) {
            var calculated = operands[0]
            for (index in 0..<operands.lastIndex) {
                val op = (operation shr index) and 0x1
                when (op) {
                    0 -> calculated += operands[index + 1]
                    1 -> calculated *= operands[index + 1]
                }
                if (calculated > result) {
                    break
                }
            }
            if (calculated == result) {
                //println ("$this matches")
                return true
            }
        }
        return false
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        val equations = input.map { line ->
            val (equation, operators) = line.split(":")
            Equation(equation.toLong(), operators.split(" ").mapToLong())
        }
        return equations.filter { it.matches() }.sumOf { it.result }
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day07_test")
    check(part1(testInput).also(::println) == 3749L)
    //check(part2(testInput).also(::println) == 1L)

    val input = readLines("Day07")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
