package org.example.models.User;
import org.example.models.Deck.Deck;
import org.example.models.Deck.DeckGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


public class UserGenerator {

    private static final AtomicInteger idCounter = new AtomicInteger(1);

    private static final String[] LOGINS = {
            "alice", "bob", "charlie", "diana", "evan",
            "fiona", "george", "helen", "ivan", "julia",
            "karl", "lisa", "mike", "nina", "oscar"
    };

    // Генерация случайного LearningAchievements (record со стриком)
    private static LearningAchievements generateRandomLearningAchievements(ThreadLocalRandom random) {
        int currentStreak = random.nextInt(0, 31); // 0–30 дней
        int longestStreak = currentStreak + random.nextInt(0, 61 - currentStreak); // от current до 60
        LocalDateTime lastStudyDate = currentStreak > 0
                ? LocalDateTime.now().minusDays(random.nextInt(currentStreak + 1))
                : null;
        return new LearningAchievements(currentStreak, longestStreak, lastStudyDate);
    }

    // Генерация случайного UserActivity (enum)
    private static UserActivity generateRandomUserActivity(ThreadLocalRandom random) {
        UserActivity[] statuses = UserActivity.values();
        return statuses[random.nextInt(statuses.length)];
    }

    public static User generateRandomUser() {
        int id = idCounter.getAndIncrement();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String baseLogin = LOGINS[random.nextInt(LOGINS.length)];
        String login = baseLogin + id;

        // Генерируем обязательные атрибуты
        UserActivity activity = generateRandomUserActivity(random);
        LearningAchievements achievements = generateRandomLearningAchievements(random);

        // Создаём пользователя
        User user = new User(id, login, activity, achievements);

        // Добавляем колоды
        int deckCount = 1 + random.nextInt(8);
        List<Deck> decks = DeckGenerator.generateDecks(deckCount);
        user.setDecks(new ArrayList<>(decks));

        return user;
    }

    public static List<User> generateUsers(int userAmount) {
        List<User> users = new ArrayList<>(userAmount);
        for (int i = 0; i < userAmount; i++) {
            users.add(generateRandomUser());
        }
        return users;
    }
}