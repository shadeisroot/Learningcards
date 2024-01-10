package com.example.learningcards;

import javafx.collections.ObservableList;

public interface Deckdao {


    void saveDeck(Deck deck);

    void getAllDecks(ObservableList<Deck> tabeldata);

    int getdeckidfromname(String deckname);

    void deleteDeck(String deckname);

    void updatecardcount(int cards, int id);

}
