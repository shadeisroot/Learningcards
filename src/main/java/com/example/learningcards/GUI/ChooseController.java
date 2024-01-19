package com.example.learningcards.GUI;

import com.example.learningcards.Business.Deck;
import com.example.learningcards.Business.Flashcard;
import com.example.learningcards.Data.Deckdao;
import com.example.learningcards.Data.DeckdaoImpl;
import com.example.learningcards.Data.Flashcardsdao;
import com.example.learningcards.Data.FlashcardsdaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ChooseController {

    private Deckdao ddi = new DeckdaoImpl();

    private Flashcardsdao fdi = new FlashcardsdaoImpl();
    private final ObservableList<Deck> deckdata = FXCollections.observableArrayList();

    @FXML
    private TableView<Deck> deckTableview;

    @FXML
    private TableColumn<Deck, Integer> deckTableviewCards;

    @FXML
    private TableColumn<Deck, String> deckTableviewName;
    private int deckidthis;
    private int deckid;

  //Close the application
    @FXML
    void closeButtonClicked(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }

//Sets the deckid and opens a new window with the flashcards for the selected deckid
    @FXML
    void continueButtonClicked(MouseEvent event) throws IOException {
        int deckid = ddi.getdeckidfromname(deckTableview.getSelectionModel().getSelectedItem().getName());
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ChooseApplication.class.getResource("questions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        QuestionsController questionsController = fxmlLoader.getController();
        questionsController.setDeckId(deckid);
        stage.setTitle("Learning Cards!");
        stage.setScene(scene);
        stage.show();
    }
//opens a new window to Create a new deck
    public void newDeckButton(MouseEvent mouseEvent) {
        Dialog<ButtonType> dialogvindue = new Dialog();
        dialogvindue.setTitle("Add Deck");
        dialogvindue.setHeaderText("Add new Deck");
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialogvindue.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        TextField name = new TextField();
        name.setPromptText("Name");
        VBox box = new VBox(name);
        box.setPrefHeight(50);
        box.setPrefWidth(300);
        dialogvindue.getDialogPane().setContent(box);

        Optional<ButtonType> button = dialogvindue.showAndWait();
        if (button.get() == addButton) {
            Deck deck = new Deck(name.getText(), 0);
            ddi.saveDeck(deck);
            ddi.getAllDecks(deckdata);
            }
        }

    public void initialize() {
        initializeDatatable();

    }
//Initializing the datatable
    public void initializeDatatable(){
        deckTableviewName.setCellValueFactory(new PropertyValueFactory<Deck, String>("name"));
        deckTableviewCards.setCellValueFactory(new PropertyValueFactory<Deck, Integer>("cards"));
        deckTableview.setItems(deckdata);
        ddi.getAllDecks(deckdata);
        deckTableview.getSelectionModel().select(0);
    }
//opens a filechooser to choose a file
    public void importDeckButton(MouseEvent mouseEvent) {
        FileChooser filechooser = new FileChooser();
        Stage stage = new Stage();
        filechooser.setTitle("title");
        File selectedFile = filechooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String media_url = selectedFile.toPath().toString();
            String filename = selectedFile.getName();

            Dialog<ButtonType> dialogvindue = new Dialog();
            dialogvindue.setTitle("Add Deck");
            dialogvindue.setHeaderText("Add new Deck");
            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialogvindue.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
            TextField name = new TextField();
            name.setPromptText("Name");
            VBox box = new VBox(name);
            box.setPrefHeight(50);
            box.setPrefWidth(300);
            dialogvindue.getDialogPane().setContent(box);

            Optional<ButtonType> button = dialogvindue.showAndWait();
            if (button.get() == addButton) {
                Deck deck = new Deck(name.getText(), 0);
                ddi.saveDeck(deck);
                ddi.getAllDecks(deckdata);
                deckidthis = ddi.getdeckidfromname(name.getText());
            }
            readLines(media_url, deckidthis);
        }
    }

//Reads the chosen textfile
    public void readLines(String filePath, int deck_id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("\t");
                if (elements.length >= 6) {
                    String answer = elements[4];
                    String question = elements[5];

                    Flashcard flashcard = new Flashcard(question, answer, deck_id);
                    fdi.saveFlashcard(flashcard);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//Deletes a deck
    public void deleteDeckButton(MouseEvent mouseEvent) {
        fdi.deleteFlashcardbydeckid(ddi.getdeckidfromname(deckTableview.getSelectionModel().getSelectedItem().getName()));
        ddi.deleteDeck(deckTableview.getSelectionModel().getSelectedItem().getName());
        deckTableview.setItems(deckdata);
        ddi.getAllDecks(deckdata);
    }
//Every time you click the tableview it updates
    public void tableviewButtonclick(MouseEvent mouseEvent) {
        deckid = ddi.getdeckidfromname(deckTableview.getSelectionModel().getSelectedItem().getName());
        System.out.println(deckid);
    }
}
