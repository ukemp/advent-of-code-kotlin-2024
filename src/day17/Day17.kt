package day17

import util.mapToInt
import util.readLines
import kotlin.time.measureTime

data class Registers(var A: Long, var B: Long, var C: Long)

fun instruction(pointer: Int, instruction: Int, operand: Int, registers: Registers, output: MutableList<Long>): Int {
    //println("Pointer: $pointer, instruction=${instructionString(instruction)} ($instruction), operand=$operand, $registers, output=$output")
    when (instruction) {
        0 -> registers.A = registers.A shr comboOf(operand, registers).toInt()
        1 -> registers.B = registers.B xor operand.toLong()
        2 -> registers.B = comboOf(operand, registers) % 8L
        3 -> if (registers.A != 0L) return operand
        4 -> registers.B = registers.B xor registers.C
        5 -> output.add(comboOf(operand, registers) % 8L)
        6 -> registers.B = registers.A shr comboOf(operand, registers).toInt()
        7 -> registers.C = registers.A shr comboOf(operand, registers).toInt()
        else -> error("Illegal instruction: $instruction")
    }
    return pointer + 2
}

fun comboOf(operand: Int, registers: Registers): Long {
    return if (operand in 0..3) {
        operand.toLong()
    } else if (operand == 4) {
        registers.A
    } else if (operand == 5) {
        registers.B
    } else if (operand == 6) {
        registers.C
    } else {
        error("Invalid operand: $operand")
    }
}

fun instructionString(operand: Int): String {
    return when (operand) {
        0 -> "adv"
        1 -> "bxl"
        2 -> "bst"
        3 -> "jnz"
        4 -> "bxc"
        5 -> "out"
        6 -> "bdv"
        7 -> "cdv"
        else -> error("Illegal operand: $operand")
    }
}

fun main() {

    fun part1(input: List<String>) {
        val output = mutableListOf<Long>()
        val A = input[0].also { check(it.startsWith("Register A:")) }.substringAfter(':').trim().toLong()
        val B = input[1].also { check(it.startsWith("Register B:")) }.substringAfter(':').trim().toLong()
        val C = input[2].also { check(it.startsWith("Register C:")) }.substringAfter(':').trim().toLong()
        val program = input[4].also { check(it.startsWith("Program:")) }.substringAfter(':').split(",").mapToInt()
        val registers = Registers(A, B, C)
        var pointer = 0
        do {
            pointer = instruction(pointer, program[pointer], program[pointer + 1], registers, output)
        } while (pointer < program.size)

        println("Part 1: ${registers}, output=${output.joinToString(separator = ",")}")
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day17_test")
    part1(testInput)
    //part2(testInput)

    val input = readLines("Day17")

    measureTime {
        part1(input)
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
