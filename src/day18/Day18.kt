package day18

import util.CharGrid
import util.Coordinate
import util.readLines
import kotlin.collections.plusAssign
import kotlin.time.measureTime


class Node(
    val position: Coordinate,
    val distance: Int = Int.MAX_VALUE,
    val previous: Node? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Node) return false
        return this.position == other.position
    }

    override fun hashCode(): Int {
        return position.hashCode()
    }

    override fun toString(): String {
        return "Node $position, d=${if (distance != Int.MAX_VALUE) distance else "undefined"}"
    }
}

fun shortestPath(grid: CharGrid, start: Coordinate, end: Coordinate): Set<Node> {
    val visited = mutableSetOf<Node>()
    val unvisited = mutableSetOf<Node>()
    unvisited.add(Node(start, 0))

    while (unvisited.isNotEmpty()) {
        val current = unvisited.minBy { it.distance }.also { visited.add(it) }
        unvisited.remove(current)
        if (current.position == end) break

        unvisited += current.position.axialNeighbors.map { position ->
            Node(position, current.distance + 1, current)
        }.filter { neighbour ->
            neighbour !in visited && grid[neighbour.position, '#'] != '#'
        }
    }

    return visited
}

fun main() {

    fun createGrid(size: Int, input: List<String>): CharGrid {
        val grid = CharGrid(buildList {
            repeat(size) {
                add(buildString { repeat(size) { append('.') } })
            }
        })
        input.map { Coordinate(it) }.forEach { c ->
            grid[c] = '#'
        }
        return grid
    }

    fun part1(size: Int, input: List<String>): Long {
        val grid = createGrid(size, input)
        val start = Coordinate(0, 0)
        val end = Coordinate(size - 1, size - 1)
        val weights = shortestPath(grid, start, end)
        val node = weights.first { it.position == end }
        val path = mutableListOf(node)

        while (path.last().previous != null) {
            path.add(path.last().previous!!)
        }
        return path.size.toLong() - 1
    }

    fun part2(size: Int, input: List<String>): Coordinate {
        val first = if (size == 7) 12 else 1024

        for (index in first..<input.size) {
            val grid = createGrid(size, input.take(index))
            val start = Coordinate(0, 0)
            val end = Coordinate(size - 1, size - 1)
            val weights = shortestPath(grid, start, end)
            val node = weights.firstOrNull { it.position == end }

            if (node == null) {
                return Coordinate(input[index - 1])
            }
        }
        error("Failed to find a non working path")
    }

    val testInput = readLines("Day18_test")
    check(part1(7, testInput.take(12)).also(::println) == 22L)
    check(part2(7, testInput).also(::println) == Coordinate(6, 1))

    val input = readLines("Day18")

    measureTime {
        println(part1(71, input.take(1024)))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(71, input))
    }.also { println("Solved part 2 in $it") }
}
