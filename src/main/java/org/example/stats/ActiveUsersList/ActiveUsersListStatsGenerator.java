package org.example.stats.ActiveUsersList;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

//Статистика по активным пользователям - возвращает список активных пользователей
public class ActiveUsersListStatsGenerator {

    // === ЗАДЕРЖКА ДЛЯ СИНХРОННЫХ (Stream) МЕТОДОВ ===
    private static void simulateBlockingDelayMicros(long delayMicros) {
        if (delayMicros <= 0) return;
        java.util.concurrent.locks.LockSupport.parkNanos(delayMicros * 1_000L); // не грузит CPU, но блокирует поток
    }

    // === Метод для Stream API (синхронный) ===
    private static User processUserBlocking(User u, long delayMicros) {
        simulateBlockingDelayMicros(delayMicros);
        return u.getUserActivity() == UserActivity.ACTIVE ? u : null;
    }

    // === ЗАДЕРЖКА ДЛЯ РЕАКТИВНЫХ (RxJava) МЕТОДОВ ===
    // Возвращает Observable, который "ждёт" delayMicros и возвращает результат
    private static io.reactivex.rxjava3.core.Observable<User> simulateReactiveDelayForUser(User u, long delayMicros) {
        if (delayMicros <= 0) {
            // Нет задержки — сразу возвращаем результат
            return io.reactivex.rxjava3.core.Observable.just(
                    u.getUserActivity() == UserActivity.ACTIVE ? u : null
            ).filter(user -> user != null);
        }
        // Используем таймер: подождать delayMicros, потом вычислить результат
        return io.reactivex.rxjava3.core.Observable.timer(delayMicros, java.util.concurrent.TimeUnit.MICROSECONDS, Schedulers.io())
                .map(tick -> u.getUserActivity() == UserActivity.ACTIVE ? u : null)
                .filter(user -> user != null);
    }

    // === Stream API с параллельными потоками ===
    public static List<User> collectActiveWithParallelStreams(List<User> users, long delayMicros) {
        return users.parallelStream()
                .map(u -> processUserBlocking(u, delayMicros))
                .filter(u -> u != null)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // === RxJava Observable с НЕБЛОКИРУЮЩЕЙ задержкой ===
    public static List<User> collectActiveWithRxJavaObservable(List<User> users, long delayMicros) {
        int maxConcurrency = Math.min(128, Math.max(8, Runtime.getRuntime().availableProcessors() * 8));

        return Observable.fromIterable(users)
                .flatMap(
                        u -> simulateReactiveDelayForUser(u, delayMicros),
                        maxConcurrency // ← очень важно!
                )
                .toList()
                .blockingGet();
    }
}