package com.derek.benchmark.count;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 20)
public class CountBenchmark {

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(CountBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private byte[] items;

    @Setup
    public void setup() {
        items = new byte[100_000];
        new Random(52487).nextBytes(items);
    }

    @Benchmark
    public int countZerosBuiltin() {
        return CountBenchmarkKt.countZerosBuiltIn(items);
    }

    @Benchmark
    public int countZerosAsInt() {
        return CountBenchmarkKt.countZerosAsInt(items);
    }

    @Benchmark
    public int countEvensBuiltin() {
        return CountBenchmarkKt.countEvensBuiltIn(items);
    }

    @Benchmark
    public int countEvensAsInt() {
        return CountBenchmarkKt.countEvensAsInt(items);
    }
}
