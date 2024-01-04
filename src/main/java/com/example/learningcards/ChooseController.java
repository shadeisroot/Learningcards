package com.example.learningcards;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class ChooseController {

    @FXML
    private TableView<?> deckTableview;

    @FXML
    private TableColumn<?, ?> deckTableviewCards;

    @FXML
    private TableColumn<?, ?> deckTableviewName;

  //Close the application
    @FXML
    void closeButtonClicked(MouseEvent event) {


    }

    @FXML
    void continueButtonClicked(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ChooseApplication.class.getResource("questions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Learning Cards!");
        stage.setScene(scene);
        stage.show();

    }

}
