package org.example.models.User;

import org.example.models.Deck.Deck;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String login;
    private LocalDateTime createdAt;
    private List<Deck> decks;
    private UserActivity userActivity;
    private LearningAchievements learningAchievements;

    public User(int id, String login, UserActivity userActivity, LearningAchievements learningAchievements) {
        this.id = id;
        this.login = login;
        this.createdAt = LocalDateTime.now();
        this.decks= new ArrayList<>();
        this.userActivity = userActivity;
        this.learningAchievements=learningAchievements;
    }

    public void setDecks(ArrayList<Deck> decks){
        this.decks=decks;
    }

    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }

    public List<Deck> getDecks() {
        return this.decks;
    }

    public UserActivity getUserActivity(){
        return this.userActivity;
    }


}
