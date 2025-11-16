package org.example.stats.ActiveUsersList;

import org.example.models.User.User;
import org.example.models.User.UserActivity;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ActiveUsersListSpliterator implements Spliterator<User> {
    private final List<User> users;
    private int start;
    private final int end;
    private final long delayMicros;

    // Защита от JIT-оптимизации
    public static volatile long SINK;

    public ActiveUsersListSpliterator(List<User> users, int start, int end, long delayMicros) {
        this.users = users;
        this.start = start;
        this.end = end;
        this.delayMicros = delayMicros;
    }

    @Override
    public boolean tryAdvance(Consumer<? super User> action) {
        if (start < end) {
            simulateDelayMicros(delayMicros);
            User u = users.get(start++);
            if (u.getUserActivity() == UserActivity.ACTIVE) {
                action.accept(u);
            }
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<User> trySplit() {
        int mid = (start + end) >>> 1;
        if (mid <= start) return null;
        int splitStart = start;
        start = mid;
        return new ActiveUsersListSpliterator(users, splitStart, mid, delayMicros);
    }

    @Override
    public long estimateSize() {
        return end - start;
    }

    @Override
    public int characteristics() {
        return SIZED | SUBSIZED | IMMUTABLE;
    }

    //Вспомогательный метод задержки
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
}