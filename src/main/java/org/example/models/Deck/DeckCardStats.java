package org.example.models.Deck;

//Для более быстрой скорости получения данных на фронтенде вместо расчета характеристик их можно сохранять в record
public record DeckCardStats(int totalCards, int studiedCards) { }
