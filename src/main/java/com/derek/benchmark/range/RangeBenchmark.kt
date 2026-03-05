package com.derek.benchmark.range

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.RunnerException
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

/*
http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
*/
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = ["-Xms2G", "-Xmx2G"])
@Warmup(iterations = 3)
@Measurement(iterations = 20)
open class RangeBenchmark {
    @Benchmark
    fun rangeForEach(blackhole: Blackhole) {
        (0 until 1_000_000).forEach {
            blackhole.consume(it)
        }
    }

    @Benchmark
    fun rangeForLoop(blackhole: Blackhole) {
        for (i in 0 until 1_000_000) {
            blackhole.consume(i)
        }
    }

    companion object {
        @Throws(RunnerException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val opt = OptionsBuilder()
                .include(RangeBenchmark::class.java.getSimpleName())
                .forks(1)
                .build()

            Runner(opt).run()
        }
    }
}
