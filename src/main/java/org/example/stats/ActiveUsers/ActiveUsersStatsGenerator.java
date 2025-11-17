package org.example.stats.ActiveUsers;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

//Статистика по активным пользователям - возвращает число активных пользователей
public class ActiveUsersStatsGenerator {

    // === ЗАДЕРЖКА ДЛЯ СИНХРОННЫХ (Stream) МЕТОДОВ ===
    private static void simulateBlockingDelayMicros(long delayMicros) {
        if (delayMicros <= 0) return;
        LockSupport.parkNanos(delayMicros * 1_000L); // не грузит CPU, но блокирует поток
    }

    // === Метод для Stream API (синхронный) ===
    private static long processUserBlocking(User u, long delayMicros) {
        simulateBlockingDelayMicros(delayMicros);
        return u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L;
    }

    // === Stream API с параллельными потоками ===
    public static long countActiveWithParallelStreams(List<User> users, long delayMicros) {
        return users.parallelStream()
                .mapToLong(u -> processUserBlocking(u, delayMicros))
                .sum();
    }

    // === ЗАДЕРЖКА ДЛЯ РЕАКТИВНЫХ (RxJava) МЕТОДОВ ===
    // Возвращает Observable, который "ждёт" delayMicros и возвращает результат
    private static Observable<Long> simulateReactiveDelayForUser(User u, long delayMicros) {
        if (delayMicros <= 0) {
            // Нет задержки — сразу возвращаем результат
            return Observable.just(
                    u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L
            );
        }
        // Используем таймер: подождать delayMicros, потом вычислить результат
        return Observable.timer(delayMicros, java.util.concurrent.TimeUnit.MICROSECONDS, Schedulers.io())
                .map(tick -> u.getUserActivity() == UserActivity.ACTIVE ? 1L : 0L);
    }

    // === RxJava Observable с НЕБЛОКИРУЮЩЕЙ задержкой ===
    public static long countActiveWithRxJavaObservable(List<User> users, long delayMicros) {
        int maxConcurrency = Math.min(128, Math.max(8, Runtime.getRuntime().availableProcessors() * 8));

        return Observable.fromIterable(users)
                .flatMap(
                        u -> simulateReactiveDelayForUser(u, delayMicros),
                        maxConcurrency // ← очень важно!
                )
                .reduce(0L, Long::sum)
                .blockingGet();
    }
}