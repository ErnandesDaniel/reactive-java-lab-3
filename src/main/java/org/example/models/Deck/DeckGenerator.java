package org.example.models.Deck;

import org.example.models.Card.Card;
import org.example.models.Card.CardGenerator;
import org.example.models.Deck.Deck;
import org.example.models.Deck.DeckAvailability;
import org.example.models.Deck.DeckCardStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class DeckGenerator {

    private static final AtomicInteger idCounter = new AtomicInteger(1);

    private static final String[] DECK_NAMES = {
            "Java Core",
            "English Vocabulary",
            "History of Russia",
            "Algorithms & Data Structures",
            "SQL Basics",
            "Web Development",
            "Math Formulas",
            "Biology Terms",
            "French Phrases",
            "Design Patterns"
    };

    public static Deck generateRandomDeck() {
        int id = idCounter.getAndIncrement();
        ThreadLocalRandom random = ThreadLocalRandom.current(); // ← вызывается в текущем потоке

        String name = DECK_NAMES[random.nextInt(DECK_NAMES.length)];

        // Случайный статус: чаще ACTIVE, реже ARCHIVED
        DeckAvailability availability = random.nextBoolean() ? DeckAvailability.ACTIVE : DeckAvailability.ARCHIVED;

        // Создаём пустую колоду
        Deck deck = new Deck(id, name, availability, new DeckCardStats(0, 0));

        // Генерируем карточки: от 5 до 25
        int cardCount = 5 + random.nextInt(21);
        List<Card> cards = CardGenerator.generateCards(cardCount);

        // Устанавливаем карточки → автоматически обновится статистика
        deck.setCards(new ArrayList<>(cards));

        return deck;
    }

    public static List<Deck> generateDecks(int deckAmount) {
        List<Deck> decks = new ArrayList<>();
        for (int i = 0; i < deckAmount; i++) {
            decks.add(generateRandomDeck());
        }
        return decks;
    }
}
