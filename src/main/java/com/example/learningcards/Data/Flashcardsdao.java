package com.example.learningcards.Data;

import com.example.learningcards.Business.Flashcard;

import java.util.List;

public interface Flashcardsdao {

    void saveFlashcard(Flashcard flashcard);

    List<Flashcard> getFlashcardsByDeckId(int deckId);

    void deleteFlashcard(String question);

    void deleteFlashcardbydeckid(int deck_id);

}
