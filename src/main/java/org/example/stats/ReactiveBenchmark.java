package org.example.stats;

import org.example.models.User.User;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
public class ReactiveBenchmark {

    @Param({"100000", "200000"})
    public int userCount;

    @Benchmark
    public void countActiveWithFlowable(Blackhole bh) {
        long result = ReactiveStatsWithFlowable.countActiveWithFlowable(userCount);
        bh.consume(result);
    }

//    @Benchmark
//    public void collectActiveWithFlowable(Blackhole bh) {
//        List<User> result = ReactiveStatsWithFlowable.collectActiveWithFlowable(userCount);
//        bh.consume(result);
//    }
}