class CharGrid(private val lines: List<String>) {

    init {
        check(lines.map { it.length }.toSet().size == 1) {
            "Not all lines have the same length: ${lines.map { it.length }}"
        }
    }

    val width = lines[0].length

    val height = lines.size

    operator fun get(x: Int, y: Int, invalid: Char? = null): Char? {
        return if (y in lines.indices && x in lines[y].indices) {
            lines[y][x]
        } else {
            invalid
        }
    }

    operator fun get(c: Coordinate, invalid: Char?) = get(c.x, c.y, invalid)

    fun forEach(block: (CharGrid, Coordinate, Char) -> Unit) {
        for (y in 0..lines.lastIndex) {
            for (x in 0..lines[0].lastIndex) {
                block(this, Coordinate(x, y), get(x, y)!!)
            }
        }
    }

    fun wordTo(from: Coordinate, relative: Coordinate): String {
        return buildString {
            for (c in from..(from + relative)) {
                this@CharGrid[c, null]?.let { append(it) }
            }
        }
    }
}