import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readText(name: String) = Path("src/$name.txt").readText().trim()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun List<String>.mapToInt() = mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null }

fun List<String>.mapToLong() = mapNotNull { if (it.isNotBlank()) it.trim().toLong() else null }

fun <T> MutableList<T>.swap(i1: Int, i2: Int) {
    val v1 = get(i1)
    set(i1, get(i2))
    set(i2, v1)
}