package day03

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import readLines

@State(Scope.Benchmark)
class Day03Benchmark() {

    /*
    main summary:
    Benchmark                 Mode  Cnt      Score     Error  Units
    Day03Benchmark.allRegex  thrpt    5   5142,924 ±  75,607  ops/s
    Day03Benchmark.indexOf   thrpt    5  19141,321 ± 155,539  ops/s
    */

    lateinit var input: List<String>

    @Setup
    fun prepare() {
        input = readLines("Day03")
    }

    @Benchmark
    fun indexOf(): Long {
        return part2IndexOf(input)
    }

    @Benchmark
    fun allRegex(): Long {
        return part2AllRegex(input)
    }
}
