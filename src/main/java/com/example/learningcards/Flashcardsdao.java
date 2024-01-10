package com.example.learningcards;

import java.util.List;

public interface Flashcardsdao {

    void saveFlashcard(Flashcard flashcard);

    List<Flashcard> getFlashcardsByDeckId(int deckId);

    void deleteFlashcard(String question);

    void deleteFlashcardbydeckid(int deck_id);

}
