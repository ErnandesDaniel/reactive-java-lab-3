package org.example.stats.ActiveUsersList;

import org.example.models.User.User;
import org.example.models.User.UserGenerator;
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
public class ActiveUsersListBenchmark {

    @Param({"3000","5000", "50000", "250000"})
    public int userCount;

//    @Param({"3000", "5000"})
//    public int userCount;

    @Param({"0", "2"})
    public int delayMicros;

    private List<User> users;

    @Setup
    public void setup() {
        users = UserGenerator.generateUsers(userCount);
    }

    @Benchmark
    public void oneStream(Blackhole bh) {
        List<User> result = ActiveUsersListStatsGenerator.collectActiveWithOneStream(users, delayMicros);
        bh.consume(result);
    }

    @Benchmark
    public void parallelStreams(Blackhole bh) {
        List<User> result = ActiveUsersListStatsGenerator.collectActiveWithParallelStreams(users, delayMicros);
        bh.consume(result);
    }

    @Benchmark
    public void customSpliterator(Blackhole bh) {
        List<User> result = ActiveUsersListStatsGenerator.collectActiveWithCustomSpliterator(users, delayMicros);
        bh.consume(result);
    }

}