package com.example.learningcards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class QuestionsController {

    List<Flashcard> flashcards = new ArrayList<>();

    @FXML
    private Label answerLabel;

    @FXML
    private ImageView questionImage;

    @FXML
    private Label questionLabel;

    @FXML
    void almcorrectButton(MouseEvent event) {

    }

    @FXML
    void correctButton(MouseEvent event) {

    }

    @FXML
    void irrelevantButton(MouseEvent event) {

    }

    @FXML
    void notCorrectButton(MouseEvent event) {

    }

    @FXML
    void partCorrectButton(MouseEvent event) {

    }

    @FXML
    void showAnswer(MouseEvent event) {

    }

}
