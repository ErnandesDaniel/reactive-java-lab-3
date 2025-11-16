package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

//Статистика по активным пользователям - возвращает число активных пользователей
public class ActiveUsersStatsGenerator {

    // Защита от JIT-оптимизации
    public static volatile long SINK;

    // Вспомогательный метод для имитации задержки (в микросекундах)
    private static void simulateDelayMicros(long delayMicros) {
        if (delayMicros <= 0) return;
        long delayNanos = delayMicros * 1_000L;
        long start = System.nanoTime();
        long counter = 0;
        while (System.nanoTime() - start < delayNanos) {
            counter++;
        }
        SINK = counter;
    }

    //Обработка пользователя с задержкой
    private static long processUserWithDelay(User u, long delayMicros) {
        simulateDelayMicros(delayMicros);
        return u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L;
    }

    // Единый вспомогательный метод работы с потоками
    private static long countActiveFromStream(Stream<User> stream, long delayMicros) {
        return stream
                .mapToLong(u -> processUserWithDelay(u, delayMicros))
                .sum();
    }

    // Stream API с одним потоком
    public static long countActiveWithOneStream(List<User> users, long delayMicros) {
        return countActiveFromStream(users.stream(), delayMicros);
    }

    // Stream API с параллельными потоками
    public static long countActiveWithParallelStreams(List<User> users, long delayMicros) {
        return countActiveFromStream(users.parallelStream(), delayMicros);
    }

    public static long countActiveWithCustomSpliterator(List<User> users, long delayMicros) {
        Spliterator.OfLong spliterator = new ActiveUsersSpliterator(users, 0, users.size(), delayMicros);
        return StreamSupport.longStream(spliterator, true).sum();
    }

    // RxJava Observable с многопоточной обработкой
    public static long countActiveWithRxJavaObservable(List<User> users, long delayMicros) {
        return Observable.fromIterable(users)
                .flatMap(u -> Observable.just(u).subscribeOn(Schedulers.computation()).map(u2 -> processUserWithDelay(u2, delayMicros)))
                .reduce(0L, (a, b) -> a + b)
                .blockingGet();
    }
}