package com.derek.benchmark.count

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.RunnerException
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = ["-Xms2G", "-Xmx2G"])
@Warmup(iterations = 3)
@Measurement(iterations = 20)
open class CountBenchmark {
    private lateinit var items: ByteArray

    @Setup
    fun setup() {
        items = ByteArray(100000)
        Random(52487).nextBytes(items)
    }

    @Benchmark
    fun countZerosBuiltin(): Int {
        return items.count { it == 0.toByte() }
    }

    @Benchmark
    fun countZerosAsInt(): Int {
        return items.countAsInt { it == 0.toByte() }
    }

    @Benchmark
    fun countEvensBuiltin(): Int {
        return items.count { it.toInt() and 1 == 0 }
    }

    @Benchmark
    fun countEvensAsInt(): Int {
        return items.countAsInt { it.toInt() and 1 == 0 }
    }

    companion object {
        @Throws(RunnerException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val opt = OptionsBuilder()
                .include(CountBenchmark::class.java.getSimpleName())
                .forks(1)
                .build()

            Runner(opt).run()
        }
    }
}

private inline fun ByteArray.countAsInt(predicate: (Byte) -> Boolean): Int {
    var count = 0
    for (item in this) {
        count += if (predicate(item)) 1 else 0
    }
    return count
}