package org.example.stats.ActiveUsers;

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
public class ActiveUsersBenchmark {

    @Param({"500", "2000"})
    public int userCount;

    @Param({"50", "500", "1000"})
    public int delayMicros;

    private List<User> users;

    @Setup
    public void setup() {
        users = UserGenerator.generateUsers(userCount);
    }

    @Benchmark
    public void parallelStreams(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithParallelStreams(users, delayMicros);
        bh.consume(result);
    }

    @Benchmark
    public void rxJavaObservable(Blackhole bh) {
        long result = ActiveUsersStatsGenerator.countActiveWithRxJavaObservable(users, delayMicros);
        bh.consume(result);
    }

}
