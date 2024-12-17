package day09

import util.readLines
import util.swap
import kotlin.time.measureTime

fun main() {

    fun parseDisk(input: String): MutableList<Int> {
        var id = 0
        val disk = mutableListOf<Int>()
        input.windowed(2, 2, true) { window ->
            repeat(window[0].toChar().digitToInt()) {
                disk.add(id)
            }
            if (window.length > 1) {
                repeat(window[1].toChar().digitToInt()) {
                    disk.add(-1)
                }
            }
            id++
        }
        return disk
    }

    fun List<Int>.findFirstBlockFromRightAt(from: Int): Int {
        for (index in from downTo 0) {
            if (this[index] != -1) return index
        }
        return -1
    }

    fun List<Int>.indexOf(id: Int, from: Int): Int {
        for (index in from..<size) {
            if (get(index) == id) return index
        }
        return -1
    }

    fun List<Int>.findContiguousBlock(id: Int, from: Int = 0): Pair<Int, Int> {
        val index = indexOf(id, from)
        if (index == -1) {
            error("Cannot find block of type: $id, starting at: $from")
        }
        var scan = index + 1
        while (scan < size) {
            if (get(scan) != id) return index to (scan - index)
            scan++
        }
        return index to (scan - index)
    }

    fun List<Int>.findFreeSpaceOfSize(size: Int): Int {
        var index = 0
        while (true) {
            val (tmp, length) = findContiguousBlock(-1, index)
            if (length >= size) {
                return tmp
            }
            index = tmp + length
            if (index >= this.size - 1) break
        }
        return -1
    }

    fun MutableList<Int>.setAll(index: Int, length: Int, value: Int) {
        for (i in index..<(index + length)) {
            set(i, value)
        }
    }

    fun List<Int>.checksum(): Long {
        return foldIndexed(0L) { index, sum, id -> if (id != -1) (sum + (index * id)) else sum }
    }

    fun part1(input: List<String>): Long {
        check(input.size == 1) { "File has more than one line" }
        val disk = parseDisk(input[0])
        var index = disk.size - 1
        var freeBlockIndex = 0

        while (index > 0) {
            val blockIndex = disk.findFirstBlockFromRightAt(index)
            if (blockIndex == -1) break
            freeBlockIndex = disk.indexOf(-1, freeBlockIndex)
            if (freeBlockIndex > blockIndex) break

            disk.swap(blockIndex, freeBlockIndex)
            index = blockIndex - 1
        }
        return disk.checksum()
    }

    fun part2(input: List<String>): Long {
        check(input.size == 1) { "File has more than one line" }
        val disk = parseDisk(input[0])
        val isMoved = mutableSetOf<Int>()
        val idToMove = disk.max()

        for (id in idToMove downTo 0) {
            val (index, length) = disk.findContiguousBlock(id)
            val free = disk.findFreeSpaceOfSize(length)
            if ((free != -1) && (free < index) && !isMoved.contains(id)) {
                disk.setAll(index, length, -1)
                disk.setAll(free, length, id)
                isMoved.add(id)
            }
        }

        return disk.checksum()
    }

    val testInput = readLines("Day09_test")
    check(part1(testInput).also(::println) == 1928L)
    check(part2(testInput).also(::println) == 2858L)

    val input = readLines("Day09")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
