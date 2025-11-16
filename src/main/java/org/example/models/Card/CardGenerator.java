package org.example.models.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CardGenerator {

    private static final AtomicInteger idCounter = new AtomicInteger(1);
    private static final Random random = new Random();

    // Базы вопросов и ответов для реализма
    private static final String[] QUESTIONS = {
            "What is Java?",
            "What is OOP?",
            "What is a record in Java?",
            "How does garbage collection work?",
            "What is the difference between == and .equals()?",
            "What is a stream in Java?",
            "What is immutability?",
            "What is an enum?",
            "What is LocalDateTime?",
            "What is spaced repetition?"
    };

    private static final String[] ANSWERS = {
            "A high-level, class-based programming language.",
            "Object-Oriented Programming: encapsulation, inheritance, polymorphism.",
            "A compact way to model immutable data carriers (since Java 14).",
            "Automatic memory management that reclaims unused objects.",
            "== compares references; .equals() compares content.",
            "A sequence of elements supporting aggregate operations.",
            "An object whose state cannot be modified after creation.",
            "A special type representing a fixed set of constants.",
            "A date-time without a time-zone in the ISO-8601 calendar system.",
            "A learning technique that increases intervals between reviews."
    };

    // Генерирует одну случайную, но валидную карточку.
    public static Card generateRandomCard() {
        int id = idCounter.getAndIncrement();
        ThreadLocalRandom random = ThreadLocalRandom.current(); // ← вызывается в текущем потоке
        int idx = random.nextInt(QUESTIONS.length);
        String question = QUESTIONS[idx];
        String answer = ANSWERS[idx];

        // Случайный уровень (всегда валидный, т.к. enum)
        CardRepetitionLevel[] levels = CardRepetitionLevel.values();
        CardRepetitionLevel level = levels[random.nextInt(levels.length)];

        // Базовая дата создания — случайная в прошлом (до 30 дней назад)
        LocalDateTime createdAt = LocalDateTime.now().minusDays(random.nextInt(31));

        // Дата последнего повторения — от createdAt до now
        long secondsBetween = java.time.Duration.between(createdAt, LocalDateTime.now()).getSeconds();
        LocalDateTime lastRepetitionDate = createdAt.plusSeconds(random.nextLong(secondsBetween + 1));

        // Следующее повторение — зависит от уровня
        LocalDateTime nextRepetitionDate = lastRepetitionDate.plusDays(level.getIntervalDays());

        Card card = new Card(id, question, answer);

        card.setCreatedAt(createdAt);
        card.setLastRepetitionDate(lastRepetitionDate);
        card.setNextRepetitionDate(nextRepetitionDate);
        card.setRetentionLevel(level);

        return card;
    }

    //Генерирует список карточек.
    public static List<Card> generateCards(int cardAmount) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardAmount; i++) {
            cards.add(generateRandomCard());
        }
        return cards;
    }
}
