package com.example.learningcards.Business;

public class Deck {
    private String name;

    private int cards;

    public Deck(String name, int cards) {
        this.name = name;
        this.cards = cards;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
