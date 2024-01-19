package com.example.learningcards.Business;

public class Flashcard {
    private String question;
    private String answer;
    private int deck_id;

    public Flashcard(String question, String answer, int deck_id) {
        this.question = question;
        this.answer = answer;
        this.deck_id = deck_id;
    }

    public int getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(int deck_id) {
        this.deck_id = deck_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}