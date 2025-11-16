package org.example.models.Card;

public enum CardRepetitionLevel {
    FIRST(0, 0), // только создана, повторить сегодня
    SECOND(1, 1), // завтра
    THIRD(2, 3), // через 3 дня
    FOURTH(3, 7), // через неделю
    FIFTH(4, 16), // через 2 недели
    SIXTH(5, 30); // раз в месяц

    private final int level;
    private final int intervalDays;

    CardRepetitionLevel(int level, int intervalDays) {
        this.level = level;
        this.intervalDays = intervalDays;
    }

    public int getLevel() {
        return level;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public CardRepetitionLevel raiseLevel() {
        if (this == SIXTH) {
            return this;
        }
        return values()[this.ordinal() + 1];
    }

    public CardRepetitionLevel resetToZero() {
        return FIRST;
    }
}