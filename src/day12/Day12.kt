package day12

import CharGrid
import Coordinate
import Direction
import readLines
import kotlin.time.measureTime

class Regions() {

    val regions = mutableMapOf<Char, MutableList<Set<Coordinate>>>()

    fun hasPlant(c: Char?, position: Coordinate): Boolean {
        if (regions.containsKey(c)) {
            for (coordinates in regions[c]!!) {
                if (position in coordinates) {
                    return true
                }
            }
        }
        return false
    }

    fun add(c: Char, region: Set<Coordinate>) {
        regions.getOrPut(c) { mutableListOf() }.add(region)
    }

    fun println() {
        for (region in regions.entries) {
            val c = region.key
            for (plant in region.value) {
                val (a, p) = areaAndPerimeter(plant)
                println("A region of $c plants with price $a * $p = ${a * p}")
            }
        }
    }

    fun price(): Long {
        return regions.values.sumOf { region ->
            region.sumOf { plant ->
                val (a, p) = areaAndPerimeter(plant)
                (a * p).toLong()
            }
        }
    }

    fun discountedPrice(): Long {
        return regions.values.sumOf { region ->
            region.sumOf { plant ->
                val (a, p) = areaAndDiscountedPerimeter(plant)
                (a * p).toLong()
            }
        }
    }
}

fun CharGrid.findRegion(position: Coordinate, region: MutableSet<Coordinate>) {
    val c = this[position, null]!!
    for (neighbour in position.axialNeighbors) {
        if (neighbour in this && neighbour !in region && this[neighbour, null] == c) {
            region.add(neighbour)
            findRegion(neighbour, region)
        }
    }
}

fun areaAndPerimeter(coordinates: Set<Coordinate>): Pair<Int, Int> {
    val perimeter = coordinates.sumOf { c ->
        4 - c.axialNeighbors.count { neighbour -> neighbour in coordinates }
    }
    return coordinates.size to perimeter
}

fun areaAndDiscountedPerimeter(coordinates: Set<Coordinate>): Pair<Int, Int> {
    var sides = 0

    listOf<Direction>(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT).forEach { direction ->
        val visited = mutableSetOf<Coordinate>()

        for (c in coordinates) {
            if (c in visited) {
                continue
            } else if ((c + direction) !in coordinates) {
                sides++
                listOf(direction.turnLeft(), direction.turnRight()).forEach { perpendicular ->
                    var next = c + perpendicular
                    while (next in coordinates && (next + direction) !in coordinates) {
                        visited.add(next)
                        next += perpendicular
                    }
                }
            }
        }
    }
    return coordinates.size to sides
}

fun main() {

    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        val regions = Regions()

        for (position in grid.coordinates()) {
            val c = grid[position, ' ']!!
            if (!regions.hasPlant(c, position)) {
                val region = mutableSetOf(position)
                grid.findRegion(position, region)
                regions.add(c, region)
            }
        }
        return regions.price()
    }

    fun part2(input: List<String>): Long {
        val grid = CharGrid(input)
        val regions = Regions()

        for (position in grid.coordinates()) {
            val c = grid[position, ' ']!!
            if (!regions.hasPlant(c, position)) {
                val region = mutableSetOf(position)
                grid.findRegion(position, region)
                regions.add(c, region)
            }
        }
        areaAndDiscountedPerimeter(regions.regions['J']!![0])
        return regions.discountedPrice()
    }

    val testInput = readLines("Day12_test")
    check(part1(testInput).also(::println) == 1930L)
    check(part2(testInput).also(::println) == 1206L)

    val input = readLines("Day12")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
