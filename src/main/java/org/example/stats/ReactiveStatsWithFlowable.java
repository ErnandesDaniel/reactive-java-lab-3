package org.example.stats;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.models.User.User;
import org.example.models.User.UserActivity;
import org.example.models.User.UserGenerator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReactiveStatsWithFlowable {

    // Собственный Subscriber для подсчета статистики с backpressure
    public static class StatsSubscriber implements Subscriber<User> {
        private Subscription subscription;
        private final AtomicLong activeCount = new AtomicLong(0);
        private final List<User> activeUsers = new ArrayList<>();
        private final AtomicInteger processed = new AtomicInteger(0);
        private final int totalElements;
        private final int batchSize = 100; // Размер батча для request

        public StatsSubscriber(int totalElements) {
            this.totalElements = totalElements;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            s.request(batchSize); // Запрашиваем первый батч
        }

        @Override
        public void onNext(User user) {
            // Обработка пользователя
            if (user.getUserActivity() == UserActivity.ACTIVE) {
                activeCount.incrementAndGet();
                synchronized (activeUsers) {
                    activeUsers.add(user);
                }
            }

            int current = processed.incrementAndGet();
            // Если обработали батч, запрашиваем следующий
            if (current % batchSize == 0 && current < totalElements) {
                subscription.request(batchSize);
            }
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {
            // Завершено
        }

        public long getActiveCount() {
            return activeCount.get();
        }

        public List<User> getActiveUsers() {
            synchronized (activeUsers) {
                return new ArrayList<>(activeUsers);
            }
        }
    }

    // Генерация пользователей с Flowable.generate() асинхронно
    public static Flowable<User> generateUsersFlowable(int count) {
        return Flowable.range(0, count)
                .map(i -> UserGenerator.generateRandomUser())
                .subscribeOn(Schedulers.io());
    }

    // Метод для подсчета с Flowable и собственным Subscriber
    public static long countActiveWithFlowable(int userCount) {
        StatsSubscriber subscriber = new StatsSubscriber(userCount);
        generateUsersFlowable(userCount)
                .blockingSubscribe(subscriber);
        return subscriber.getActiveCount();
    }

//    // Метод для сбора списка с Flowable и собственным Subscriber
//    public static List<User> collectActiveWithFlowable(int userCount) {
//        StatsSubscriber subscriber = new StatsSubscriber(userCount);
//        generateUsersFlowable(userCount)
//                .blockingSubscribe(subscriber);
//        return subscriber.getActiveUsers();
//    }
}