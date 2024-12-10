class CharGrid(input: List<String>) {

    private val lines = input.toMutableList()

    init {
        check(lines.map { it.length }.toSet().size == 1) {
            "Not all lines have the same length: ${lines.map { it.length }}"
        }
    }

    val width = lines[0].length

    val height = lines.size

    val horizontalIndices = IntRange(0, width - 1)

    val verticalIndices = IntRange(0, height - 1)

    operator fun get(x: Int, y: Int, invalid: Char? = null): Char? {
        return if (y in lines.indices && x in lines[y].indices) {
            lines[y][x]
        } else {
            invalid
        }
    }

    operator fun get(c: Coordinate, invalid: Char?) = get(c.x, c.y, invalid)

    operator fun set(c: Coordinate, newChar: Char): CharGrid {
        lines[c.y] = "${lines[c.y].substring(0..<c.x)}$newChar${lines[c.y].substring(c.x + 1)}"
        return this
    }

    operator fun contains(c: Coordinate): Boolean = c.x in horizontalIndices && c.y in verticalIndices

    fun coordinates() = sequence<Coordinate> {
        for (y in verticalIndices) {
            for (x in horizontalIndices) {
                yield(Coordinate(x, y))
            }
        }
    }

    fun filter(predicate: (Coordinate, Char) -> Boolean) = sequence<Coordinate> {
        for (y in verticalIndices) {
            for (x in horizontalIndices) {
                val c = Coordinate(x, y)
                if (predicate(c, get(x, y)!!)) {
                    yield(c)
                }
            }
        }
    }

    fun all(predicate: (Coordinate, Char) -> Boolean): Set<Char> {
        return buildSet {
            for (y in verticalIndices) {
                for (x in horizontalIndices) {
                    val c = get(x, y)!!
                    if (predicate(Coordinate(x, y), c)) {
                        add(c)
                    }
                }
            }
        }
    }

    fun indexOf(search: Char): Coordinate {
        for (y in verticalIndices) {
            val index = lines[y].indexOf(search)
            if (index != -1) {
                return Coordinate(index, y)
            }
        }
        error("Couldn't find character '$search'")
    }

    fun wordTo(from: Coordinate, relative: Coordinate): String {
        return buildString {
            for (c in from..(from + relative)) {
                this@CharGrid[c, null]?.let { append(it) }
            }
        }
    }

    override fun toString(): String {
        return lines.joinToString(separator = "\n")
    }
}