package com.example.learningcards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

import java.io.IOException;
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

    public void initializeflashcards(){
        if (!flashcards.isEmpty()) {
            cardCountLabel.setText(String.valueOf(getNumberOfCards()) + " " + "Cards Remaining");
            questionLabel.setText(flashcards.get(currentIndex).getQuestion());
        } else {
            questionLabel.setText("No flashcards available for this deck");
        }

    }

    public void moveFlashcardBackByPercentage(int index, double percentage) {
        if (index >= 0 && index < flashcards.size()) {
            Flashcard flashcard = flashcards.get(index);
            int targetPosition = (int) (index - (flashcards.size() * percentage / 100));
            targetPosition = Math.max(targetPosition, 0);
            flashcards.remove(flashcard);
            flashcards.add(targetPosition, flashcard);
        }
    }


    @FXML
    void almcorrectButton(MouseEvent event) {
        moveFlashcardBackByPercentage(0, 10);
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

    @FXML
    void notCorrectButton(MouseEvent event) {
        moveFlashcardBackByPercentage(0, 80);
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

    @FXML
    void showAnswer(MouseEvent event) {
        if (!flashcards.isEmpty()) {
            answerLabel.setText("Answer: " + flashcards.get(currentIndex).getAnswer());
        }
    }

    public int getNumberOfCards() {
        ddi.updatecardcount(flashcards.size(),deckid);
        return flashcards.size();
    }

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


    public void restartButton(MouseEvent mouseEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ChooseApplication.class.getResource("questions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Learning Cards!");
        stage.setScene(scene);
        stage.show();
    }

    public void setDeckId(int deckid) {
        System.out.println(deckid);
        this.deckid = deckid;
        this.flashcards = fdi.getFlashcardsByDeckId(this.deckid);
        initializeflashcards();
        ddi.updatecardcount(flashcards.size(),deckid);
    }

}
