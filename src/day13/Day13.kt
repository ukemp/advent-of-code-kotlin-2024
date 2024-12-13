package day13

import readLines
import kotlin.time.measureTime

data class ClawMachine(val ax: Long, val ay: Long, val bx: Long, val by: Long, val px: Long, val py: Long) {

    fun solveOrNull(): Pair<Long, Long>? {
        val i = ((py * bx) - (by * px)) / ((ay * bx) - (by * ax))
        val j = (px - (ax * i)) / bx
        val checkX = (ax * i) + (bx * j)
        val checkY = (ay * i) + (by * j)

        return if (px == checkX && py == checkY) i to j else null
    }

    override fun toString(): String {
        return """
            Button A: X+${ax}, Y+${ay}
            Button B: X+${bx}, Y+${by}
            Prize: X=${px}, Y=${py}""".trimIndent()
    }

    companion object {
        fun from(input: List<String>, correction: Long = 0): List<ClawMachine> {
            return input.windowed(4, 4, true).map { lines ->
                check(lines[0].startsWith("Button A"))
                check(lines[1].startsWith("Button B"))
                check(lines[2].startsWith("Prize:"))
                val xreg = """X\+(\d+),""".toRegex()
                val yreg = """Y\+(\d+)""".toRegex()
                val pxreg = """X=(\d+),""".toRegex()
                val pyreg = """Y=(\d+)""".toRegex()
                val ax = xreg.find(lines[0])!!.groups[1]!!.value.toLong()
                val ay = yreg.find(lines[0])!!.groups[1]!!.value.toLong()
                val bx = xreg.find(lines[1])!!.groups[1]!!.value.toLong()
                val by = yreg.find(lines[1])!!.groups[1]!!.value.toLong()
                val px = pxreg.find(lines[2])!!.groups[1]!!.value.toLong() + correction
                val py = pyreg.find(lines[2])!!.groups[1]!!.value.toLong() + correction
                ClawMachine(ax, ay, bx, by, px, py)
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val machines = ClawMachine.from(input)
        return machines.mapNotNull { it.solveOrNull() }.sumOf { result ->
            ((result.first * 3) + result.second)
        }
    }

    fun part2(input: List<String>): Long {
        val machines = ClawMachine.from(input, 10000000000000L)
        return machines.mapNotNull { it.solveOrNull() }.sumOf { result ->
            ((result.first * 3) + result.second)
        }
    }

    val testInput = readLines("Day13_test")
    check(part1(testInput).also(::println) == 480L)

    val input = readLines("Day13")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
