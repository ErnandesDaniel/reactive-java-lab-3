package org.example.models.Deck;

import org.example.models.Card.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Deck {

    private int id;
    private String name;
    private LocalDateTime createdAt;
    private ArrayList<Card> cards;
    private DeckAvailability deckAvailability;
    private DeckCardStats deckCardStats;

    public Deck(int id,  String name, DeckAvailability deckAvailability, DeckCardStats deckCardStats) {
        this.id = id;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.cards = new ArrayList();
        this.deckAvailability = deckAvailability;
        this.deckCardStats = deckCardStats;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards=cards;
        this.deckCardStats= new DeckCardStats(cards.size(), 0);
    }

    public void addCard(Card card) {
        this.cards.add(card);
        this.deckCardStats= new DeckCardStats(this.deckCardStats.totalCards() +1, 0);
    }
}
