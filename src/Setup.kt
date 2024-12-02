import java.io.FileNotFoundException
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.useLines
import kotlin.io.path.writeLines

fun main() {
    val src = Path("./src").absolute()
    val template = src.resolve("template/DayXX.kt")
    if (!template.exists()) {
        throw FileNotFoundException("Missing $template")
    }

    for (day in 1..25) {
        val day = String.format("%02d", day)
        val packageName = "day$day"
        val path = src.resolve(packageName)

        if (!path.exists()) {
            path.createDirectory()
            val dst = path.resolve("Day$day.kt")
            template.useLines { lines ->
                dst.writeLines(lines.map { line ->
                    if (line.startsWith("package")) {
                        "package $packageName"
                    } else if (line.contains("\"DayXX")) {
                        line.replace("\"DayXX", "\"Day$day")
                    } else {
                        line
                    }
                })
            }
        } else {
            println("Skipping $path...")
        }

        listOf(
            src.resolve("Day${day}_test.txt"),
            src.resolve("Day$day.txt")
        ).forEach { txt ->
            if (!txt.exists()) txt.createFile()
        }
    }
}