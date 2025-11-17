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
    private static User processUserWithDelay(User u, long delayMicros) {
        simulateDelayMicros(delayMicros);
        return u.getUserActivity() == UserActivity.ACTIVE ? u : null;
    }

    // Единый вспомогательный метод работы с потоками
    private static List<User> collectActiveFromStream(Stream<User> stream, long delayMicros) {
        return stream
                .map(u -> processUserWithDelay(u, delayMicros))
                .filter(u -> u != null)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Stream API с параллельными потоками
    public static List<User> collectActiveWithParallelStreams(List<User> users, long delayMicros) {
        return collectActiveFromStream(users.parallelStream(), delayMicros);
    }

    // RxJava Observable с многопоточной обработкой для сбора списка
    public static List<User> collectActiveWithRxJavaObservable(List<User> users, long delayMicros) {
        return Observable.fromIterable(users)
                .flatMap(u -> Observable.just(u).subscribeOn(Schedulers.computation()).flatMap(u2 -> {
                    User processed = processUserWithDelay(u2, delayMicros);
                    return processed != null ? Observable.just(processed) : Observable.empty();
                }))
                .toList()
                .blockingGet();
    }
}