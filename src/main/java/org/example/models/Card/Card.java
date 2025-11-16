package org.example.models.Card;
import java.time.LocalDateTime;

public class Card {

    private int id;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime lastRepetitionDate;
    private LocalDateTime nextRepetitionDate;
    private CardRepetitionLevel retentionLevel;

    public Card(int id, String question, String answer) {
        this.id = id;
        this.answer = answer;
        this.question = question;

        LocalDateTime now =LocalDateTime.now();

        this.createdAt = now;
        this.lastRepetitionDate= now;
        this.nextRepetitionDate= now;
        this.retentionLevel = CardRepetitionLevel.FIRST;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastRepetitionDate() {
        return lastRepetitionDate;
    }

    public LocalDateTime getNextRepetitionDate() {
        return nextRepetitionDate;
    }

    public CardRepetitionLevel getRetentionLevel() {
        return retentionLevel;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastRepetitionDate(LocalDateTime lastRepetitionDate) {
        this.lastRepetitionDate = lastRepetitionDate;
    }

    public void setNextRepetitionDate(LocalDateTime nextRepetitionDate) {
        this.nextRepetitionDate = nextRepetitionDate;
    }

    public void setRetentionLevel(CardRepetitionLevel retentionLevel) {
        this.retentionLevel = retentionLevel;
    }
}