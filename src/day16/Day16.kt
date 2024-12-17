package day16

import util.CharGrid
import util.Coordinate
import util.Direction
import util.readLines
import java.util.PriorityQueue
import kotlin.time.measureTime

class Node(
    val position: Coordinate,
    val direction: Direction,
    val distance: Int = Int.MAX_VALUE
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
    val unvisited = PriorityQueue<Node>(compareBy { it.distance })
    unvisited.add(Node(start, Direction.RIGHT, 0))

    while (unvisited.isNotEmpty()) {
        val current = unvisited.remove().also { visited.add(it) }
        if (current.position == end) break

        val left = current.direction.turnLeft()
        val right = current.direction.turnRight()

        unvisited += listOf(
            Node(current.position + left, left, current.distance + 1001),
            Node(current.position + right, right, current.distance + 1001),
            Node(current.position + current.direction, current.direction, current.distance + 1),
        ).filter { node ->
            node !in visited && grid[node.position, '#'] != '#'
        }
    }

    return visited
}

data class Position(val position: Coordinate, val direction: Direction)

data class Path(val pos: Position, val distance: Int, val previous: List<Position>)

fun allShortestPaths(grid: CharGrid, start: Coordinate, end: Coordinate): Set<Coordinate> {
    val visited = mutableSetOf<Position>()
    val unvisited = PriorityQueue<Path>(compareBy { it.distance })
    val bestPoints = mutableSetOf(end)
    var bestCost = Int.MAX_VALUE
    unvisited.add(Path(Position(start, Direction.RIGHT), 0, emptyList()))

    while (unvisited.isNotEmpty()) {
        val current = unvisited.remove().also { visited.add(it.pos) }
        if (current.pos.position == end) {
            if (current.distance <= bestCost) {
                bestCost = current.distance
                bestPoints.addAll(current.previous.map { it.position })
            } else {
                break
            }
        }

        val left = current.pos.direction.turnLeft()
        val right = current.pos.direction.turnRight()

        unvisited += listOf(
            Path(Position(current.pos.position + left, left), current.distance + 1001, current.previous + current.pos),
            Path(
                Position(current.pos.position + right, right),
                current.distance + 1001,
                current.previous + current.pos
            ),
            Path(
                Position(current.pos.position + current.pos.direction, current.pos.direction),
                current.distance + 1,
                current.previous + current.pos
            ),
        ).filter { path ->
            path.pos !in visited && grid[path.pos.position, '#'] != '#'
        }
    }

    return bestPoints
}

fun main() {
    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        val start = grid.indexOf('S')
        val end = grid.indexOf('E')
        val nodes = shortestPath(grid, start, end)
        val destination = nodes.first { it.position == end }

        return destination.distance.toLong()
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)
        val start = grid.indexOf('S')
        val end = grid.indexOf('E')
        val nodes = allShortestPaths(grid, start, end)

        return nodes.size.toLong()
    }

    val testInput = readLines("Day16_test")
    check(part1(testInput).also(::println) == 11048L)
    check(part2(testInput).also(::println) == 64L)

    val input = readLines("Day16")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
