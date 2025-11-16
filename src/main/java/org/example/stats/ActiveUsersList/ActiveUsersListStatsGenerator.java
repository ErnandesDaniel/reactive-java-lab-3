package org.example.stats.ActiveUsersList;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;
import java.util.ArrayList;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    // Stream API с одним потоком
    public static List<User> collectActiveWithOneStream(List<User> users, long delayMicros) {
        return collectActiveFromStream(users.stream(), delayMicros);
    }

    // Stream API с параллельными потоками
    public static List<User> collectActiveWithParallelStreams(List<User> users, long delayMicros) {
        return collectActiveFromStream(users.parallelStream(), delayMicros);
    }

    public static List<User> collectActiveWithCustomSpliterator(List<User> users, long delayMicros) {
        Spliterator<User> spliterator = new ActiveUsersListSpliterator(users, 0, users.size(), delayMicros);
        return StreamSupport.stream(spliterator, true)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}