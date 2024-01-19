package com.example.learningcards.GUI;

import com.example.learningcards.Business.Flashcard;
import com.example.learningcards.Data.Deckdao;
import com.example.learningcards.Data.DeckdaoImpl;
import com.example.learningcards.Data.Flashcardsdao;
import com.example.learningcards.Data.FlashcardsdaoImpl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class QuestionsController {

    private int deckid;

    public TextArea questiontextField;
    public Label questionLabel;
    public Label cardCountLabel;
    ChooseController chooseController = new ChooseController();
    Flashcardsdao fdi = new FlashcardsdaoImpl();
    Deckdao ddi = new DeckdaoImpl();

    List<Flashcard> flashcards = fdi.getFlashcardsByDeckId(deckid);
    private int currentIndex = 0;

    @FXML
    private Label answerLabel;

    @FXML
    private ImageView questionImage;


    public void initialize() {
    }
//Inistializing the flashcards
    public void initializeflashcards(){
        if (!flashcards.isEmpty()) {
            cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            questionLabel.setText(flashcards.get(currentIndex).getQuestion());
        } else {
            questionLabel.setText("No flashcards available for this deck");
        }

    }
//Moves the flashcard back by a certain percentage
    public void moveFlashcardBackByPercentage(int index, double percentage) {
        if (index >= 0 && index < flashcards.size()) {
            Flashcard flashcard = flashcards.get(index);
            int targetPosition = (int) (index - (flashcards.size() * percentage / 100));
            targetPosition = Math.max(targetPosition, 0);
            flashcards.remove(flashcard);
            flashcards.add(targetPosition, flashcard);
        }
    }

//Moves the card back by 20 % when clicked the almost correct button and shows the next card in line
    @FXML
    void almcorrectButton(MouseEvent event) {
        moveFlashcardBackByPercentage(0, 20);
        try {
            if (!flashcards.isEmpty()) {
                currentIndex = (currentIndex + 1) % flashcards.size();
                questionLabel.setText(flashcards.get(currentIndex).getQuestion());
                answerLabel.setText("");
                cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            } else {
                questionLabel.setText("theres no more cards");
            }
        } catch (ArithmeticException e) {
            cardCountLabel.setText("0" + " " + " " + "Cards Remaining");
            questionLabel.setText("theres no more cards");
        }

    }
//Removes the flashcard tempoarily and shows the next card in line
    @FXML
    void correctButton(MouseEvent event) {

        try {
            if (!flashcards.isEmpty()) {
                flashcards.remove(currentIndex);
                currentIndex = (currentIndex + 1) % flashcards.size();
                questionLabel.setText(flashcards.get(currentIndex).getQuestion());
                answerLabel.setText("");
                cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            } else {
                questionLabel.setText("theres no more cards");
            }
        } catch (ArithmeticException e) {
            cardCountLabel.setText("0" + " " + " " + "Cards Remaining");
            questionLabel.setText("theres no more cards");
        }
    }

//Removes the flashcard entirely and shows the next card in line
    @FXML
    void irrelevantButton(MouseEvent event) {
        fdi.deleteFlashcard(flashcards.get(0).getQuestion());
        try {
            if (!flashcards.isEmpty()) {
                flashcards.remove(currentIndex);
                currentIndex = (currentIndex + 1) % flashcards.size();
                questionLabel.setText(flashcards.get(currentIndex).getQuestion());
                answerLabel.setText("");
                cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            } else {
                questionLabel.setText("theres no more cards");
            }
        } catch (ArithmeticException e) {
            cardCountLabel.setText("0" + " " + " " + "Cards Remaining");
            questionLabel.setText("theres no more cards");
        }
    }
//Moves the flashcard back by 60 percent and shows the next card in line
    @FXML
    void notCorrectButton(MouseEvent event) {
        moveFlashcardBackByPercentage(0, 60);
        try {
            if (!flashcards.isEmpty()) {
                currentIndex = (currentIndex + 1) % flashcards.size();
                questionLabel.setText(flashcards.get(currentIndex).getQuestion());
                answerLabel.setText("");
                cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            } else {
                questionLabel.setText("theres no more cards");
            }
        } catch (ArithmeticException e) {
            cardCountLabel.setText("0" + " " + " " + "Cards Remaining");
            questionLabel.setText("theres no more cards");
        }

    }
//Moves the flashcard back by 40 percent and shows the next card in line
    @FXML
    void partCorrectButton(MouseEvent event) {
        moveFlashcardBackByPercentage(0, 40);
        try {
            if (!flashcards.isEmpty()) {
                currentIndex = (currentIndex + 1) % flashcards.size();
                questionLabel.setText(flashcards.get(currentIndex).getQuestion());
                answerLabel.setText("");
                cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            } else {
                questionLabel.setText("theres no more cards");
            }
        } catch (ArithmeticException e) {
            cardCountLabel.setText("0" + " " + " " + "Cards Remaining");
            questionLabel.setText("theres no more cards");
        }
    }
//Shows the answer to the question
    @FXML
    void showAnswer(MouseEvent event) {
        if (!flashcards.isEmpty()) {
            answerLabel.setText("Answer: " + flashcards.get(currentIndex).getAnswer());
        }
    }
//Updates the number of cards count
    public int getNumberOfCards() {
        ddi.updatecardcount(flashcards.size(),deckid);
        return flashcards.size();
    }
//Opens a new window to be able to create a new flashcard
    public void newCardButton(MouseEvent mouseEvent) {

        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setTitle("Add Card");

        GridPane dialogLayout = new GridPane();
        dialogLayout.setHgap(10);
        dialogLayout.setVgap(10);
        dialogLayout.setPadding(new Insets(10));

        TextField QuestionTextField = new TextField();
        TextField AnswerTextField = new TextField();
        TextField deckidTextField = new TextField();

        dialogLayout.add(new Label("Question:"), 0, 0);
        dialogLayout.add(QuestionTextField, 1, 0);
        QuestionTextField.setText("");


        dialogLayout.add(new Label("Answer:"), 0, 1);
        dialogLayout.add(AnswerTextField, 1, 1);
        AnswerTextField.setText("");

        dialogLayout.add(new Label("Deckid:"), 0, 2);
        dialogLayout.add(deckidTextField, 1, 3);
        deckidTextField.setText(String.valueOf(deckid));


        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {

            Flashcard flashcard = new Flashcard(QuestionTextField.getText(), AnswerTextField.getText(), Integer.parseInt(deckidTextField.getText()));

            fdi.saveFlashcard(flashcard);
            ddi.updatecardcount(flashcards.size(),deckid);
            editStage.close();
        });

        dialogLayout.add(submitButton, 1, 5);

        Scene editScene = new Scene(dialogLayout, 300, 250);
        editStage.setScene(editScene);

        editStage.showAndWait();
    }

//Setdeckid which is used to initialize the application
    public void setDeckId(int deckid) {
        System.out.println(deckid);
        this.deckid = deckid;
        this.flashcards = fdi.getFlashcardsByDeckId(this.deckid);
        initializeflashcards();
        ddi.updatecardcount(flashcards.size(),deckid);
    }

}
